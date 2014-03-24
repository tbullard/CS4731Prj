package ann;

import java.io.Serializable;
import java.util.List;

public class Datum implements Serializable {

    private static final long serialVersionUID = -1301665872366708846L;

    private List<Double> attributesList;
    private List<Double> classificationList;

    public Datum(List<Double> attributesList, List<Double> classificationList) {
        this.attributesList = attributesList;
        this.classificationList = classificationList;
        return;
    }

    public Datum(List<Double> attributesList) {
        this(attributesList, null);
        return;
    }

    public Double getAttributeAt(int index) {
        return attributesList.get(index);
    }

    public Double getClassificationAt(int index) {
        return classificationList.get(index);
    }

    public List<Double> getAttributes() {
        return attributesList;
    }

    public List<Double> getClassificaitons() {
        return classificationList;
    }
}
