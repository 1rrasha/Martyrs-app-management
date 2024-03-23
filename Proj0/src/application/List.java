package application;

public interface List<T> {
	void insert(T element);

	boolean delete(T element);

	void display();

	Martyr search(String name);
}
