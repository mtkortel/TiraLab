/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tiralab.huffman.rakenne;

/**
 *
 * @author mkortelainen
 */
public class StringArray {
    int koko = 64;
    String[] avain = new String[koko];
    String[] arvo = new String[koko];
    public int length = 0;
    
    public void put(String key, String value){
        if (key != null && value != null){
            if (length == avain.length){
                kasvata();
            }
            avain[length] = key;
            arvo[length] = value;
            length++;
        }
    }

    private void kasvata() {
        koko = koko * 2;
        String[] av2 = new String[koko];
        String[] ar2 = new String[koko];
        for (int i=0; i < avain.length; i++){
            av2[i] = avain[i];
            ar2[i] = arvo[i];
        }
        avain = av2;
        arvo = ar2;
    }

    public boolean containsKey(String etsijä) {
        boolean abc=true;
        for (int i = 0; i < length; i++){
            if (avain[i] != null){
                if (avain[i].equals(etsijä)){
                    return true;
                }
            }
        }
        return false;
    }
    public String get(String key){
        for (int i = 0; i < length; i++){
            if (avain[i] != null){
                if (avain[i].equals(key)){
                    return arvo[i];
                }
            }
        }
        return null;
    }

    public String[] keys() {
        return avain;
    }

    public String[] values() {
        return arvo;
    }
}
