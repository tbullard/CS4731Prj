package ann;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;

import dk.itu.mario.MarioInterface.GamePlay;
import dk.itu.mario.level.generator.PlayerStyle;

public class PlayerModelModule implements Serializable {
    
    private static final long serialVersionUID = 7862570198496526836L;
    
    FFNeuralNetwork network;
    
    public PlayerModelModule(FFNeuralNetwork network) {
        this.network = network;
        return;
    }
    
    public Double[] classifyGamePlay(String filePath) {
        Double[] classDistro = {0.25, 0.25, 0.25, 0.25};
        FileInputStream fis = null;
        ObjectInputStream in = null;
        GamePlay gp =  null;
        try {
            fis = new FileInputStream(filePath);
            in = new ObjectInputStream(fis);
            gp = (GamePlay)in.readObject();
            in.close();
        } catch (Exception e) {
            return classDistro;
        }
        double leftVsright = gp.timeRunningLeft / (double)(gp.timeRunningLeft + gp.timeRunningRight + 1);
        double weightedDeaths = ((gp.timesOfDeathByFallingIntoGap * 2) + (gp.timesOfDeathByGoomba * 1.8) + 
                                (gp.timesOfDeathByGreenTurtle * 1.5) + (gp.timesOfDeathByRedTurtle * 1.5) + 
                                (gp.timesOfDeathByCannonBall * 1.25) + (gp.timesOfDeathByChompFlower * 1.25) +
                                (gp.timesOfDeathByJumpFlower * 1.25) + (gp.timesOfDeathByArmoredTurtle));
        double weightedKills = ((gp.JumpFlowersKilled * 2) + (gp.ChompFlowersKilled * 1.75 ) + 
                               (gp.ArmoredTurtlesKilled * 1.5) + (gp.CannonBallKilled * 1.35) + 
                               (gp.RedTurtlesKilled * 1.15) + (gp.GreenTurtlesKilled * 1.15) + 
                               (gp.GoombasKilled)) / (double)(gp.totalEnemies + 1);
        double weightedBlocks = ((gp.emptyBlocksDestroyed * 2) + (gp.coinBlocksDestroyed * 1.5) + 
                                (gp.powerBlocksDestroyed)) / (double)(gp.totalpowerBlocks + 
                                gp.totalCoinBlocks + gp.totalEmptyBlocks + 1);
        double coinPercent = gp.coinsCollected / (double)(gp.totalCoins + 1);
        double aimlessJumpsPercent = (gp.aimlessJumps + 1) / (double)(gp.jumpsNumber + 1);
        ArrayList<Double> parsedData = (ArrayList<Double>) Datum.packValues(leftVsright, weightedDeaths, weightedKills, 
                                                                            weightedBlocks, coinPercent, aimlessJumpsPercent);
        Datum gameData = new Datum(parsedData);
        ArrayList<Double> networkClassificaiton = (ArrayList<Double>) network.classifyInstance(gameData);
        classDistro = networkClassificaiton.toArray(classDistro);
        return classDistro;
    }
    
    public PlayerStyle getStyle(Double[] classDistro) {
        PlayerStyle domStyle = null;
        double max = Double.MIN_VALUE;
        int maxIndex = 0;
        for(int i = 0; i < classDistro.length; i++) {
            if(classDistro[i] > max) {
                max = classDistro[i];
                maxIndex = i;
            }
        }
        if(maxIndex == 0) {
            domStyle = PlayerStyle.NEW;
        } else if(maxIndex == 1) {
            domStyle = PlayerStyle.KILLER;
        } else if(maxIndex == 2) {
            domStyle = PlayerStyle.EXPLORER;
        } else {
            domStyle = PlayerStyle.SPEED;
        }
        return domStyle;
    }
    
    public static PlayerModelModule read(String filePath) {
        FileInputStream fis = null;
        ObjectInputStream in = null;
        PlayerModelModule pmm =  null;
        try {
            fis = new FileInputStream(filePath);
            in = new ObjectInputStream(fis);
            pmm = (PlayerModelModule)in.readObject();
            in.close();
        } catch (Exception e) {
            return null;
        }
        return pmm;
    }
}
