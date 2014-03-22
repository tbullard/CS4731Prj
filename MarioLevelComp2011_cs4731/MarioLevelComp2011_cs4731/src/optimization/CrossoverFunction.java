package optimization;

import java.util.List;

public interface CrossoverFunction<T> {
	
	abstract public List<Individual<T>> mate(Individual<T> father, Individual<T> mother);
}
