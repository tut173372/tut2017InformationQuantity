
package s4.b173372; // Please modify to s4.Bnnnnnn, where nnnnnn is your student ID.
import java.lang.*;
import s4.specification.*;


public class Frequencer implements FrequencerInterface{
    // Code to start with: This code is not working, but good start point to work.
    byte [] myTarget;
    byte [] mySpace;
    boolean targetReady = false;
    boolean spaceReady = false;
    int [] suffixArray;
    // The variable, "suffixArray" is the sorted array of all suffixes of mySpace.
    // Each suffix is expressed by a interger, which is the starting position in mySpace. // The following is the code to print the variable
    private void printSuffixArray() {
        if(spaceReady) {
            for(int i=0; i< mySpace.length; i++) {
                int s = suffixArray[i];
                for(int j=s;j<mySpace.length;j++) {
                    System.out.write(mySpace[j]); }
                System.out.write('\n'); }
        }
    }
    
    
    private int suffixCompare(int i, int j) {
        // comparing two suffixes by dictionary order.
        // i and j denoetes suffix_i, and suffix_j
        // if suffix_i > suffix_j, it returns 1
        // if suffix_i < suffix_j, it returns -1
        // if suffix_i = suffix_j, it returns 0;
        // It is not implemented yet,
        // It should be used to create suffix array.
        // Example of dictionary order
        // "i"< "o"  : compare by code
        // "Hi"< "Ho"  ; if head is same, compare the next element
        // "Ho"< "Ho "  ; if the prefix is identical, longer string is big
        
        int si = suffixArray[i];
        int sj = suffixArray[j];
        int s = 0;
        if(si>s)s=si;
        if(sj>s)s=sj;
        int n=mySpace.length -s;
        for(int  k=0;k<n;k++){
            if(mySpace[si+k]>mySpace[sj+k]) return 1;
            if(mySpace[si+k]<mySpace[sj+k]) return -1;
        }
        if(si<sj) return 1;
        if(si>sj) return -1;
        
        
        
        
        return 0;
    }
    
    
    //クイックソート
     //*********************************************************************************************
    /*
     * 軸要素の選択
     * 順に見て、最初に見つかった異なる2つの要素のうち、
     * 大きいほうの番号を返します。
     * 全部同じ要素の場合は -1 を返します。
     */
    int pivot(int i,int j){
        int k=i+1;
        /*
        //while(k<=j && suffixArray[i]==suffixArray[k]) k++;
        while(k<=j && (suffixCompare(i,k)==0) )k++;
        
        if(k>j) return -1;
        
        //if(suffixArray[i]>=suffixArray[k]) return i;
        if((suffixCompare(i,k)==0)||(suffixCompare(i,k)==1)) return i;
        */
        if(k>j) return -1;
        return k;
    }
    
    /*
     * パーティション分割
     * a[i]～a[j]の間で、x を軸として分割します。
     * x より小さい要素は前に、大きい要素はうしろに来ます。
     * 大きい要素の開始番号を返します。
     */
    int partition(int i,int j,int x){
        int l=i,r=j;
        
        // 検索が交差するまで繰り返します
        while(l<=r){
            // 軸要素以上のデータを探します
            //while(l<=j && suffixArray[l]<x)  l++;
            while(l<=j && (suffixCompare(x,l)==1)) l++;
            // 軸要素未満のデータを探します
            //while(r>=i && suffixArray[r]>=x) r--;
            while(r>=i && (suffixCompare(r,x)!=-1)) r--;
            
            if(l>r) break;
            int t=suffixArray[l];
            suffixArray[l]=suffixArray[r];
            suffixArray[r]=t;
            l++; r--;
        }
        return l;
    }
    
    /*
     * クイックソート（再帰用）
     * 配列aの、a[i]からa[j]を並べ替えます。
     */
    public void quickSort(int i,int j){
        if(i==j) return;
        int p=pivot(i,j);
        if(p!=-1){
            int k=partition(i,j,p);
            quickSort(i,k-1);
            quickSort(k,j);
        }
    }
    
    /*
     * ソート
     */
    //public void sort(int[] a){
        //quickSort(a,0,a.length-1);
    //}
    //*********************************************************************************************
    
    public void setSpace(byte []space) {
        mySpace = space;
        if(mySpace.length>0) spaceReady = true;
        suffixArray = new int[space.length];
        // put all suffixes in suffixArray. Each suffix is expressed by one interger.
        
        for(int i = 0; i< space.length; i++) {
            suffixArray[i] = i;
        }
        //ソート
        int value;
        for(int i=0;i<space.length-1;i++){
            for(int j=i+1;j<space.length;j++){
                if(suffixCompare(i,j) > 0){
                    value = suffixArray[i];
                    suffixArray[i] = suffixArray[j];
                    suffixArray[j] = value;
                }
            }
        }
        
        //quickSort(0,suffixArray.length-1);
        //quickSort(0,suffixArray.length-1);
        
        
        // Sorting is not implmented yet.
        /* Example from "Hi Ho Hi Ho"
         0: Hi Ho
         1: Ho
         2: Ho Hi Ho
         3:Hi Ho
         4:Hi Ho Hi Ho
         5:Ho
         6:Ho Hi Ho
         7:i Ho
         8:i Ho Hi Ho
         9:o
         A:o Hi Ho
         */
        printSuffixArray();
    }
    
    
    private int targetCompare(int i, int start, int end) {
        // It is called from subBytesStarIndex, adn subBytesEndIndex.
        // "start" and "end" are same as in subByteStartIndex, and subByteEndIndex ** // target_start_end is subBytes(start, end) of target **
        // comparing suffix_i and target_start_end by dictonary order with limitation of length; ***
        // if the beginning of suffix_i matches target_start_end, and suffix is longerthan target it returns 0;
        // if suffix_i > target_start_end it return 1; **
        // if suffix_i < target_start_end it return -1 **
        
        // It should be used to search the apropriate index of some suffix.
        // Example of search
        
        // suffix target
        // "o" > i
        // "o" < z
        // "o" = o
        // "o" < oo
        // "Ho" > Hi
        // "Ho" < Hz
        // "Ho" = Ho
        //"Ho"  < "Ho "
        // "Ho" = "H"
        
        
        int si = mySpace.length-suffixArray[i];
        int sj = end;
        int s=0;
        if(si>sj)s=sj;
        else s=si;
        
        for(int  k=0;k<s;k++){
            
            if(mySpace[suffixArray[i]+k]>myTarget[k]) return -1;
            if(mySpace[suffixArray[i]+k]<myTarget[k]) return 1;
        }
        if((si>sj) || (si==sj))
            return 0;
        else if(si<sj)
            return -1;
        
        
        return -5;

    
    }
    
    private int subByteStartIndex(int start, int end) {
        // It returns the index of the first suffix which is equal or greater than subBytes; // not implemented yet;
        // For "Ho", it will return 5 for "Hi Ho Hi Ho".
        // For "Ho ", it will return 6 for "Hi Ho Hi Ho".
        int flag=0;
        for(int i=0;i<suffixArray.length;i++){
            if((i==0)&&(targetCompare(i,start,end)==0))//最初の文字だけの処理
                return 0;
            if(targetCompare(i,start,end)==1)
                flag=1;
            else if((targetCompare(i,start,end)==0)&&(flag==1))
                return i;
            else if((targetCompare(i,start,end)==-1))
                return -1;
            
        }
        return 0;
    }
    
    
    private int subByteEndIndex(int start, int end) {
        // It returns the next index of the first suffix which is greater than subBytes; // not implemented yet
        // For "Ho", it will return 7 for "Hi Ho Hi Ho".
        // For "Ho ", it will return 7 for "Hi Ho Hi Ho".
        int flag=0;
        for(int i=suffixArray.length-1;i>=0;i--){
            if((i==suffixArray.length-1)&&(targetCompare(i,start,end)==0))//最後の文字の処理
                return suffixArray.length;
            if(targetCompare(i,start,end)==-1)
                flag=1;
            else if((targetCompare(i,start,end)==0)&&(flag==1))
                
                return i+1;
            else if((targetCompare(i,start,end)==1))
                return -1;
            
        }
        return 0;
    }
    
    
    public int subByteFrequency(int start, int end) {
        // This method could be defined as follows though it is slow.
        
        int spaceLength = mySpace.length;
        int count = 0;
        for(int offset = 0; offset< spaceLength - (end - start); offset++) {
            boolean abort = false;
            for(int i = 0; i< (end - start); i++) {
                if(myTarget[start+i] != mySpace[offset+i]) { abort = true; break; } }
            if(abort == false) { count++; } }
        
        int first = subByteStartIndex(start,end);
        int last1 = subByteEndIndex(start, end);
        //inspection code
         for(int k=start;k<end;k++)
         { System.out.write(myTarget[k]); }
        System.out.printf(": first=%d last1=%d\n", first, last1);
        
        return last1 - first;
        
        
    }
    
    
    public void setTarget(byte [] target) {
        myTarget = target;
        if(myTarget.length>0) targetReady = true; }
    
    public int frequency() {
        if(targetReady == false) return -1;
        if(spaceReady == false) return 0;
        return subByteFrequency(0, myTarget.length);
        
        
    }
    public static void main(String[] args) {
        Frequencer frequencerObject;
        try {
            frequencerObject = new Frequencer();
            frequencerObject.setSpace("Hi Ho Hi Ho".getBytes());
            
             frequencerObject.setTarget("Ho".getBytes());
            
             int result = frequencerObject.frequency();
             
             System.out.print("Freq = "+ result+" ");
             if(4 == result) { System.out.println("OK"); }
             else {System.out.println("WRONG"); }
            
        }
        catch(Exception e) {
            System.out.println("STOP"); }
    }
}
