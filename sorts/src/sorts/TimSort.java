package sorts;

import java.util.Comparator;

/*
 * Timsort. В алгоритме сочетаются сортировка вставками и слиянием.
 * Осно-вой принципа этого алгоритма является идея,
 * что сортируемые массивы со-держат в себе уже упорядоченные подмассивы.
 * На таких данных Timsort рабо-тает значительно быстрее многих других алгоритмов.
 * Основная идея состоит в том, что входной алгоритм разделяется на под-массивы,
 * которые упорядочивают сортировкой вставками.
 *  Затем отсортиро-ванные подмассивы собираются в единый массив с помощью некоторой модификации сортировки слиянием.
 */
public class TimSort implements Sort {
	
	//Минимально возможная длина упорядоченного подмассива
	private static final int MIN_RUN = 32;
	//Необходимое количество элементовб взятых из одного подмассива во время слияния, для начала галопа.
	private static final int MIN_GALOP = 7;
	
	//стэк
	private int stackStart[]; //массив хранящий начальные индексы под-массивов
	private int stackLen[]; //массив хранящий длины под-массивов
	private int stackSize; //размер стэка
	
	//Объект для сравнения элементов
	private Comparator<Object> comparator;
	
	@Override
	public <V extends Comparable<V>> void sort(V[] array) {
		
		TimSorting(array, (v1, v2) -> v1.compareTo(v2));
		
	}

	@Override
	public <V> void sort(V[] array, Comparator<V> comparator) {
		
		TimSorting(array, comparator);
		
	}
	
	
	@SuppressWarnings("unchecked")
	private <V> void TimSorting(V[] array, Comparator<V> comparator) {
		
		this.comparator = (Comparator<Object>) comparator;
		
		//если длина массива <= минимально возможной длины подмассива - сортируем ставками
		if (array.length <= MIN_RUN) InsertSorting(array, 0, array.length);
		else { //иначе - выполняем TimSort
			
			int minRun = countMinRun(array.length); //подсчёт минимальной длины подмассива
			
			//создание стэка размером, которой подходит для хранения всех подмассивов
			initStack(array.length / minRun + 1); 
			
			int i = 0; //указатель на начало текущего подмассива
			int len = array.length;
			
			while (len != 0) {
				
				int run = countRun(array, i); //рассчёт длины текущего подмассива
				
				/*
				 * Если длина текущего подмассива меньше минимальной,
				 * то увеличиваем её и сортируем вставками.
				 */
				if (run < minRun) {
					
					run = (minRun > len) ? len : minRun;
					
					InsertSorting(array, i, i + run);
					
				}
				
				push(i, run); //добавляем текущий подмассив в стэк
								
				merge(array); //слияние подмассивов
				
				i += run;
				len -= run;
				
			}
			
			mergeForce(array);
			
		}
		
	}
	
	private int countMinRun(int len) {
		
		int p = 0;
		
		while (len >= MIN_RUN) {
			
			p |= len & 1;
			
			len >>= 1;
			
		}
		
		return len + p;
		
	}

	private void InsertSorting(Object[] array, int start, int end) {
		
		for (int i = start; i < end ; i++) {
			
			Object value = array[i];
			
			int j = i - 1;
			
			while (j >= start && comparator.compare(value, array[j]) < 0) {
				
				array[j + 1] = array[j];
				j--;
				
			}
			
			array[j+1] = value;
			
		}
		
	}
	
	private int countRun(Object[] array, int start) {
		
		int i = start + 1;
		
		if (i == array.length) return 1;
		
		if (comparator.compare(array[i - 1], array[i++]) > 0) {
			
			while (i < array.length && comparator.compare(array[i - 1], array[i]) > 0) i++;	
			
			reverse(array, start, i);
			
		} else while (i < array.length && comparator.compare(array[i - 1], array[i]) <= 0) i++;
		
		return i - start;
	}
	
	private void reverse(Object[] array, int l, int r) {
		
		r--;
		
		while (l <= r) swap(array, l++, r--);
		
	}
	
	private void initStack(int capacity) {
		
		stackStart = new int[capacity];
		stackLen = new int[capacity];
		
	}
	
	private void push(int start, int len) {
		
		stackStart[stackSize] = start;
		stackLen[stackSize++] = len;
		
	}
	
	private void pop() {
		
		stackStart[--stackSize] = 0;
		stackLen[stackSize] = 0;
		
	}
	
	private void merge(Object[] array) {
		
		 while (stackSize > 1) {
	            int n = stackSize - 2;
	            if (n > 0 && stackLen[n-1] <= stackLen[n] + stackLen[n+1] ||
	                n > 1 && stackLen[n-2] <= stackLen[n] + stackLen[n-1]) {
	                if (stackLen[n - 1] < stackLen[n + 1])
	                    n--;
	            } else if (n < 0 || stackLen[n] > stackLen[n + 1]) {
	                break;
	            }
	            
	            if (stackLen[n] < stackLen[n+1]) mergeLeft(array, n, n + 1);
	            else mergeRight(array, n, n + 1);
	            
	            stackLen[n] += stackLen[n+1];
	            
	            if (n == stackSize - 3) {
	                stackStart[n + 1] = stackStart[n + 2];
	                stackLen[n + 1] = stackLen[n + 2];
	            }
	            
	            pop();
	            
	        }
		
	}
	
	private void mergeForce(Object[] array) {
		
		while(stackSize > 1) {
			
			int n = stackSize - 2;
			
			if (n > 0 && stackLen[n - 1] < stackLen[n + 1]) n--;
	            
			if (stackLen[n] < stackLen[n+1]) mergeLeft(array, n, n + 1);
            else mergeRight(array, n, n + 1);
            
            stackLen[n] += stackLen[n+1];
            
            if (n == stackSize - 3) {
                stackStart[n + 1] = stackStart[n + 2];
                stackLen[n + 1] = stackLen[n + 2];
            }
            
            pop();
			
		}
		
	}
	
	private void mergeLeft(Object[] array, int l, int r) {
		
		Object[] tmp = new Object[stackLen[l]];
		
		for (int i = 0; i < stackLen[l]; i++) tmp[i] = array[stackStart[l] + i];
		
		int indexLeft = 0, indexRight = stackStart[r], index = stackStart[l];
		int lenLeft = stackLen[l], lenRight = stackLen[r] + stackStart[r];
		
		int countGalop = 0, flagGalop = -1, indexGalop;
		int compare;
				
		while (indexLeft < lenLeft && indexRight < lenRight) {
			
			compare = comparator.compare(tmp[indexLeft], array[indexRight]);
			
			if (countGalop == MIN_GALOP) {
				
				if (flagGalop == l && compare < 0) {
					
					indexGalop = binarySearch(tmp, array[indexRight], l, lenLeft - 1, true);
					while (indexLeft < indexGalop) array[index++] = tmp[indexLeft++];
					
				} else if (compare >= 0) {
					
					indexGalop = binarySearch(array, tmp[indexLeft], r, lenRight - 1, true);
					while (indexRight < indexGalop) array[index++] = array[indexRight++];
					
				}
				
			} else {
			
				if (compare < 0) {
					
					array[index++] = tmp[indexLeft++]; 
					
					if (flagGalop != l) {
						flagGalop = l;
						countGalop = 0;
					}
					
				} else {
					
					array[index++] = array[indexRight++];
					
					if (flagGalop != r) {
						flagGalop = r;
						countGalop = 0;
					}
					
				}
			
			}
			
		}
		
		while (indexLeft < lenLeft) array[index++] = tmp[indexLeft++];
		//while (indexRight < lenRight) array[index++] = array[indexRight++];
		
	}
	
	private void mergeRight(Object[] array, int l, int r) {
		
		Object[] tmp = new Object[stackLen[r]];
		
		for (int i = 0; i < stackLen[r]; i++) tmp[i] = array[stackStart[r] + i];
		
		int indexLeft = stackStart[l] + stackLen[l] - 1,
				indexRight = stackLen[r] - 1,
				index = stackStart[l] + stackLen[l] + stackLen[r] - 1,
				startLeft = stackStart[l];
		
		int countGalop = 0, flagGalop = -1, indexGalop;
		int compare;
		
		while (indexLeft >= startLeft && indexRight >= 0) {
			
			compare = comparator.compare(array[indexLeft], tmp[indexRight]);
			
			if (countGalop == MIN_GALOP) {
				
				if (flagGalop == l && compare > 0) {
					
					indexGalop = binarySearch(array, tmp[indexRight], startLeft, indexLeft, false);
					while (indexLeft >= indexGalop) array[index--] = tmp[indexLeft--];
					
				} else if (compare <= 0) {
					
					indexGalop = binarySearch(tmp, array[indexLeft], 0, indexRight, false);
					while (indexRight >= indexGalop) array[index--] = array[indexRight--];
					
				}
				
			} else {
			
				if (compare > 0) {
					
					array[index--] = array[indexLeft--]; 
					
					if (flagGalop != l) {
						flagGalop = l;
						countGalop = 0;
					}
					
				} else {
					
					array[index--] = tmp[indexRight--];
					
					if (flagGalop != r) {
						flagGalop = r;
						countGalop = 0;
					}
					
				}
			
			}
			
		}
		
		//while (indexLeft >= stackStart[l]) array[index--] = array[indexLeft--];
		while (indexRight >= 0) array[index--] = tmp[indexRight--];
		
	}
	
	/*
	 * Бинарный поиск
	 * Если последний параметр true - возвращает последнее вхождение элемента в массив
	 * Иначе возвращает первое вхождение
	 */
	private int binarySearch(Object[] array, Object tmp, int l, int r, boolean last) {

	    while (l <= r) {
	    	
	        int m = (l + r) >> 1;
	     	int compare = comparator.compare(array[m], tmp);

	        if (compare < 0 || (last && compare == 0))
	            l = m + 1;
	        else if (compare > 0 || (!last && compare == 0))
	            r = m - 1;
	    }
	    
	    return last ? r : l;
		
	}
	
}
