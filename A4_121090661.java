import java.util.ArrayList;
import java.util.Scanner;

public class A4_121090661 {

    public static class Triple<T> {
        int row,column;
        T item;

        public Triple() {
            row = 0;
            column = 0;
            item = null;
        }

        public Triple(int row,int column,T item) {
            this.row = row;
            this.column = column;
            this.item = item;
        }
    }

    
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
			
        String sizeString = cin.nextLine();
		String[] s1 = sizeString.split(", ");
		int rows = Integer.parseInt(s1[0]);
		int cols = Integer.parseInt(s1[1]);
        int length1 = 0;
        int length2 = 0;
        ArrayList<Integer> list1 = new ArrayList<>();
        ArrayList<Integer> list2 = new ArrayList<>();
        for(int i=0;i<rows;i++){
            String addString = cin.nextLine();
            if(!addString.endsWith(":")){
                String[] s3 = addString.split(" ");
                length1 += s3.length-1;
                for(int j=1;j<s3.length;j++){
                    String[] s4 = s3[j].split(":");
                    int col = Integer.parseInt(s4[0]);
                    int data = Integer.parseInt(s4[1]);
                    list1.add(i);
                    list1.add(col-1);
                    list1.add(data);
                }
            }
        }      

        sizeString = cin.nextLine();
        for(int i=0;i<rows;i++){
            String addString = cin.nextLine();
            if(!addString.endsWith(":")){
				String[] s3 = addString.split(" ");
                length2 += s3.length-1;
				for(int j=1;j<s3.length;j++){
					String[] s4 = s3[j].split(":");
					int col = Integer.parseInt(s4[0]);
					int data = Integer.parseInt(s4[1]);
					list2.add(i);
                    list2.add(col-1);
                    list2.add(data);
                    }
                }
            } 
        Triple<Integer>[] triples1 = new Triple[length1];
        Triple<Integer>[] triples2 = new Triple[length2];
        for (int i = 0;i < triples1.length; i ++) {
            triples1[i] = new Triple<>();
            triples1[i].row = list1.remove(0);
            triples1[i].column = list1.remove(0);
            triples1[i].item = list1.remove(0);
        }
        for (int i = 0;i < triples2.length; i ++) {
            triples2[i] = new Triple<>();
            triples2[i].row = list2.remove(0);
            triples2[i].column = list2.remove(0);
            triples2[i].item = list2.remove(0);
        }
        Triple<Integer>[] triples3 = add(triples1,triples2);
        System.out.println(rows + ", " + cols);
		String output = ""; // set default to empty/all zero matrix
		int[] visited = new int[rows];
		for(int k=0;k<rows;k++){
			output = output + '\n' + (k+1) + " :";
            for (int j=0 ; j<triples3.length; j++){
				if (triples3[j].row==k){
                    if (visited[k]!=1){
                        output = output.substring(0,output.length()-2);
						visited[k] = 1;
                    }
                    if (visited[k]==1){
					output = output + " " + (triples3[j].column+1) + ":" + triples3[j].item;
					}
                }
                else{
                    continue;
                }
			}
        }
        System.out.println(output);
    }

    @SuppressWarnings("unchecked")
    public static Triple<Integer>[] add(Triple<Integer>[] triples1,Triple<Integer>[] triples2) {
        Triple<Integer>[] triples0 = new Triple[triples1.length + triples2.length];
        int count = 0;
        int i = 0;
        int j = 0;
        while (i < triples1.length && j < triples2.length) {
            if (triples1[i].row == triples2[j].row) {
                if (triples1[i].column < triples2[j].column) {
                    triples0[count] = new Triple<>();
                    triples0[count].row = triples1[i].row;
                    triples0[count].column = triples1[i].column;
                    triples0[count].item = triples1[i].item;
                    count ++;
                    i ++;
                } else if (triples1[i].column > triples2[j].column) {
                    triples0[count] = new Triple<>();
                    triples0[count].row = triples2[j].row;
                    triples0[count].column = triples2[j].column;
                    triples0[count].item = triples2[j].item;
                    count ++;
                    j ++;
                } else {
                    if (triples1[i].item + triples2[j].item != 0) {
                        triples0[count] = new Triple<>();
                        triples0[count].row = triples1[i].row;
                        triples0[count].column = triples2[j].column;
                        triples0[count].item = triples1[i].item + triples2[j].item;
                        count++;
                    }
                    i++;
                    j++;
                }
            } else if (triples1[i].row > triples2[j].row) {
                triples0[count] = new Triple<>();
                triples0[count].row = triples2[j].row;
                triples0[count].column = triples2[j].column;
                triples0[count].item = triples2[j].item;
                count ++;
                j++;
            } else {
                triples0[count] = new Triple<>();
                triples0[count].row = triples1[i].row;
                triples0[count].column = triples1[i].column;
                triples0[count].item = triples1[i].item;
                count ++;
                i++;
            }
        }
        while (i < triples1.length || j < triples2.length) {
            if (j == triples2.length) {
                triples0[count] = new Triple<>();
                triples0[count].row = triples1[i].row;
                triples0[count].column = triples1[i].column;
                triples0[count].item = triples1[i].item;
                count++;
                i++;
            } else {
                triples0[count] = new Triple<>();
                triples0[count].row = triples2[j].row;
                triples0[count].column = triples2[j].column;
                triples0[count].item = triples2[j].item;
                count++;
                j++;
            }
        }
        Triple<Integer>[] triples = new Triple[count];
        for (int k = 0;k < count; k ++) {
            triples[k] = new Triple<>();
            triples[k].row = triples0[k].row;
            triples[k].column = triples0[k].column;
            triples[k].item = triples0[k].item;
        }
        return triples;
    }
}