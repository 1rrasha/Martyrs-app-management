package application;

import java.util.Arrays;

public class MartyrList<T extends Martyr> implements List<T> {

	// attributes
	private T[] data;
	private int count = 0;

	// constructor
	public MartyrList(int capacity) {
		data = (T[]) new Object[capacity];
	}

	// getters
	public T[] getData() {
		return data;
	}

	public int getCount() {
		return count;
	}

	// methods

	@Override
	public void insert(T martyr) {
		if (count < data.length) {
			data[count] = martyr;
			count++;
		} else {
			System.out.println("List is full. Cannot insert more martyrs.");
		}
	}

	@Override
	public boolean delete(T martyr) {
		for (int i = 0; i < count; i++) {
			if (data[i].equals(martyr)) {
				System.arraycopy(data, i + 1, data, i, count - i - 1);
				data[count - 1] = null;
				count--;
				return true;
			}
		}
		return false;
	}

	@Override

	public Martyr search(String name) {
		for (int i = 0; i < count; i++) {
			if (data[i].getName().equals(name)) {
				return data[i];
			}
		}
		return null; // Martyr not found in the list
	}

	@Override
	public void display() {
		System.out.println("Martyrs List:");
		System.out.println("-----------------");
		System.out.printf("%-20s %-5s %-15s %-15s %-10s %n", "Name", "Age", "Location", "Date of Death", "Gender");
		System.out.println("-----------------");

		for (int i = 0; i < count; i++) {
			System.out.printf("%-20s %-5d %-15s %-15s %-10s %n", data[i].getName(), data[i].getAge(),
					data[i].getElocation(), data[i].getDateofdeath(), data[i].getGender());
		}
	}

}
