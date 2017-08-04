
import java.util.Scanner;
import java.io.File;

import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

/* this is your main class and is what runs
 when the programs is started.
 */
public class parallelCounter {
  
  static int userInput = 0;
  static Scanner fileInput = new Scanner(System.in);
  static int currentSize = Globals.mainArray.length;
  
  public static void main(String[] args) {
    /*System.out.println("How big do you want the array to be?");
    userInput = fileInput.nextInt();*/
    System.out.println("Building the array. Please wait...");
    /*Globals.maxArraySize = (userInput * 2) + 1;
    System.out.println("maxArraySize is: " + Globals.maxArraySize);
    Globals.mainArray = new int[Globals.maxArraySize];*/
    buildArray();
    int currentSize = Globals.maxArraySize;
    Scanner in = new Scanner(System.in);
    System.out.println("How many threads would you like to generate?");
    int numThreads = in.nextInt();
    int length = Globals.mainArray.length;
    //System.out.println("length is: " + length);
    int offset = length / numThreads;
    //System.out.println("offset is: " + offset);
    int extra = length % numThreads;
    //System.out.println("extra is: " + extra);
    int begin = 0;
    int end = 0;
    MyThread[] worker = new MyThread[numThreads];
    long start = System.nanoTime();
    for (int i = 0; i < numThreads; i++) {
      //System.out.println("running for loop iteration " + i);
      begin = end;
      if (begin != 0 && end != 0) {
        begin = begin + 1;
      }
      if (i < extra) {
        end = begin + offset;
      } else {
        end = begin + offset - 1;
      }
     //System.out.println("starting worker " + i + " with begin:" + begin + " and end:" + end);
      worker[i] = new MyThread(begin, end, "worker " + i);
      worker[i].start();
      try {
        worker[i].join();
      } catch (InterruptedException e) {
        Logger.getLogger(parallelCounter.class.getName()).log(Level.SEVERE, null, e);
      }
      
    }
    begin = 0;
    int middle = 0;
    end = 0;
    for(int arrCnt=0; arrCnt < Globals.mainArray.length; arrCnt++) {
      Globals.finalArray[arrCnt] = Globals.mainArray[arrCnt];
    }
    for (int i = 0; i < numThreads - 1; i++) {
      //System.out.println("running for loop iteration " + i);
      if (middle != 0 && end != 0) {
        middle = end + 1;
        end = middle + offset - 1;
      }
      if (i == 0) {
        middle = begin + offset;
        end = middle + offset - 1;
      }
      if (i < extra) {
        middle = middle + 1;
        end = middle + offset - 1;
        //System.out.println("middle1 is: " + middle);
        //System.out.println("end1 is: " + end);
        merge(begin, middle, end);
      } else {
        end = middle + offset - 1;
        //System.out.println("middle is: " + middle);
        //System.out.println("end is: " + end);
        merge(begin, middle, end);
      }
    }
    long progend = System.nanoTime();
    /*System.out.println("Printing Array: ");
    for(int i = 0; i<Globals.mainArray.length; i++){
      System.out.println(Globals.finalArray[i]);
    }*/
    long runtime = progend - start;
    System.out.println("Runtime is: " + runtime + "nanoseconds");
  }
  
  public static void buildArray() {
    try {
      File inputFile = new File("randomNumbers.txt");
      Scanner textFileInput = new Scanner(inputFile);      //set scanner input to external text file from inputFile
      int i = 0;
      while (textFileInput.hasNext()) {
        int x = textFileInput.nextInt();
        Globals.mainArray[i] = x;
        i++;
      }
      textFileInput.close();
    } catch (FileNotFoundException e) {
      Logger.getLogger(parallelCounter.class.getName()).log(Level.SEVERE, null, e);
    }
  }
  
  private static void merge(int leftPos, int rightPos, int rightEnd) {
    Scanner in = new Scanner(System.in);
    int leftEnd = rightPos - 1;
    int tmpPos = leftPos;
    int numElements = rightEnd - leftPos + 1;
    //System.out.println("printing left array, leftEnd is: "+leftEnd);
    for(int arrCnt=leftPos; arrCnt<=leftEnd; arrCnt++) {
      //System.out.println("element " + arrCnt + ": " + Globals.mainArray[arrCnt]);
     // String moveToNext = in.nextLine();
    }
    //System.out.println("printing right array, rightEnd is: "+rightEnd);
    for(int arrCnt=rightPos; arrCnt<=rightEnd; arrCnt++) {
      //System.out.println("element " + arrCnt + ": " + Globals.mainArray[arrCnt]);
      //String moveToNext = in.nextLine();
    }
    //System.out.println("merging left and right arrays");
    while (leftPos <= leftEnd && rightPos <= rightEnd) {
      //System.out.println("comparing left-" + leftPos + " = " + Globals.mainArray[leftPos] + " and right-" + rightPos + " = " + Globals.mainArray[rightPos]);
      if (Globals.mainArray[leftPos] > Globals.mainArray[rightPos]) {
        //System.out.println("inserting from left");
        //System.out.println("Globals.mainArray[leftPos+ 1] is:  " + Globals.mainArray[leftPos + 1]);
        //System.out.println("leftPos is: " + leftPos + " before insert");
        Globals.finalArray[tmpPos++] = Globals.mainArray[leftPos++];
        //System.out.println("leftPos is: " + leftPos + " after insert");
        
      } else {
        //System.out.println("inserting from right");
        //System.out.println("rightPos is: " + rightPos);
        //System.out.println("Globals.mainArray[rightPos+ 1] is:  " + Globals.mainArray[rightPos + 1]);
       // System.out.println("rightPos is: " + rightPos + " before insert");
        Globals.finalArray[tmpPos++] = Globals.mainArray[rightPos++];
        //System.out.println("rightPos is: " + rightPos + " after insert");
      }
      //String moveToNext = in.nextLine();
    }
    /* System.out.println("Printing Array: ");
     for(int i = 0; i<Globals.mainArray.length; i++){
     System.out.println(Globals.mainArray[i]);
     } */
    //System.out.println("leftPos: " + leftPos + " and rightPos: " + rightPos);
    int tmpCntr = 0;
    while (leftPos <= leftEnd) {
      if(tmpCntr==0) {
        //System.out.println("right is empty, filling from left");
        tmpCntr++;
      }
      //System.out.println("right is empty, filling from left");
      Globals.finalArray[tmpPos++] = Globals.mainArray[leftPos++];
    }
    while (rightPos <= rightEnd) {
      if(tmpCntr==0) {
        //System.out.println("left is empty, filling from right");
        tmpCntr++;
      }
      // System.out.println("left is empty, filling from right");
      Globals.finalArray[tmpPos++] = Globals.mainArray[rightPos++];
    }
    for (int i = 0; i < numElements; i++, rightEnd--) {
      Globals.mainArray[rightEnd] = Globals.finalArray[rightEnd];
    }
  }
  
  private static void mergeSort(int left, int right) {
    if (left < right) {
      int center = (left + right) / 2;
      mergeSort(left, center);
      mergeSort(center + 1, right);
      merge(left, center + 1, right);
    }
  }
  
  public static void mergeSort() {
    int tmpArray[] = new int[Globals.mainArray.length];
    mergeSort(0, Globals.mainArray.length - 1);
  }
  
}


/*
 This is the thread class.  This is what is used for
 mt[i] array element in the main class.  Notice 2 constructors,
 one default, MyThread(), and one that take a string for a name,
 MyThread(String name).  "super()" is built in and references the Thread
 class which MyThread extends.  The getName() function is also built into
 the Thread class, which is set with super(name).
 the run() function is what runs when mt[i].start() is called.
 */
class MyThread extends Thread {
  
  int begin;
  int end;
  
  MyThread() {
  }
  
  MyThread(String name) {
    super(name);
  }
  
  MyThread(int begin1, int end1, String name) {
    super(name);
    begin = begin1;
    end = end1;
    //System.out.println("Thread" + getName() + " Recieved: " + begin + " & " + end);
  }
  
  public void run() {
    mergeSort(begin, end);
    for (int x = 0; x < 20; x++) {
      //System.out.println(getName() + ": " + x);
    }
  }
  
  private static void mergeSort(int left, int right) {
    if (left < right) {
      int center = (left + right) / 2;
      mergeSort(left, center);
      mergeSort(center + 1, right);
      merge(left, center + 1, right);
    }
  }
  
  public static void mergeSort() {
    mergeSort(0, Globals.mainArray.length - 1);
  }
  
  private static void merge(int leftPos, int rightPos, int rightEnd) {
    int leftEnd = rightPos - 1;
    int tmpPos = leftPos;
    int numElements = rightEnd - leftPos + 1;
    while (leftPos <= leftEnd && rightPos <= rightEnd) {
      if (Globals.mainArray[leftPos] > Globals.mainArray[rightPos]) {
        Globals.finalArray[tmpPos++] = Globals.mainArray[leftPos++];
      } else {
        Globals.finalArray[tmpPos++] = Globals.mainArray[rightPos++];
      }
    }
    while (leftPos <= leftEnd) {
      Globals.finalArray[tmpPos++] = Globals.mainArray[leftPos++];
    }
    while (rightPos <= rightEnd) // Copy rest
    {
      Globals.finalArray[tmpPos++] = Globals.mainArray[rightPos++];
    }
    for (int i = 0; i < numElements; i++, rightEnd--) {
      Globals.mainArray[rightEnd] = Globals.finalArray[rightEnd];
    }
  }
  
}
