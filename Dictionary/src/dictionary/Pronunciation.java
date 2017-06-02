package dictionary;

/**
 *
 * @author SUJAN
 */
public class Pronunciation {
    String pronunciation = "";
    String text = "";
    int length;
    public Pronunciation(String tx, int l){
        text = tx;
        length = l;
    }
    String pronunciationFinding(){
        for(int i=0;i<length;i++){
            if(text.charAt(i)=='['){
                if(text.contains("]")){
                    for(int j = i+1; text.charAt(j)!=']';j++){
                        pronunciation+=text.charAt(j); 
                    }
                    break;
                }
                else{
                    pronunciation="";
                    break;
                }
            }
        }
        return pronunciation;
    }
    
    String[] pronunciationSeparator(){
        String[] tx = pronunciation.split(",");
        return tx;
    }
}
