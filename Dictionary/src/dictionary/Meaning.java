package dictionary;
/**
 *
 * @author SUJAN
 */
public class Meaning {
    String meaning = "";
    String text = "";
    int length;
    public Meaning(String tx, int l){
        text = tx;
        length = l;
    }
    String meaningFinding(){
        String interested;
        if(text.contains("]")){
            for(int i=0;i<length;i++){       
                if(text.charAt(i)==']'){
                    String[] str = text.split("]");
                    if(str.length <2)
                        break;
                    else{
                        if(str[1].isEmpty())
                            interested= str[2];
                        else
                            interested=str[1];
                        interested = interested.trim();
                        if(interested.charAt(0)=='(' && interested.contains(")")){
                            interested = interested.substring(interested.indexOf(")")+1,interested.length());
                        }
                        interested = interested.trim();
                        if(interested.contains(" "))
                            interested=interested.substring(interested.indexOf(0x020),interested.length());
                        else interested = "";
                        interested=interested.trim();
                        if(interested.contains(Character.toString((char)0x964))){
                            meaning=interested.substring(0,interested.indexOf(0x964));
                        }
                        else if(interested.contains("("))
                            meaning=interested.substring(0,interested.indexOf("("));
                        else if(interested.contains("{"))
                            meaning=interested.substring(0,interested.indexOf("{"));
                        else if(interested.contains(";"))
                            meaning=interested.substring(0,interested.indexOf(";"));
                        
                        meaning=meaning.replaceAll(Character.toString((char)0x09e7),"");//for bengali one
                        meaning=meaning.replaceAll(Character.toString((char)0x09e8),"");//for bengali two
                        meaning=meaning.replaceAll(Character.toString((char)0x09e9),"");//for bengali three
                        meaning = meaning.trim();
                        break;
                    }
                }
            }
        }
        return meaning;
    }
    
    String[] meaningSeparator(){
        String[] tx = meaning.split(";");
        return tx;
    }
}


/*
                        for(int j=0;j<interested.length();j++){
                            if(interested.codePointAt(j)==0x964){
                                meaning=interested.substring(0,j);
                                meaning=meaning.replaceAll(Character.toString((char)0x09e7),"");//for bengali one
                                meaning = meaning.trim();
                                return meaning;
                            }
                        }
                        */
                        //return interested;


/*
                else if(text.charAt(i)==']' && occurance==2){
                    String tmp = text.substring(pos2+1,text.length());
                    tmp = tmp.trim();
                    //String[] str = text.split("]");
                    if(tmp.length()<2)
                        break;
                    else{
                        String interested = tmp;
                        interested=interested.substring(interested.indexOf(0x020),interested.length());
                        interested=interested.trim();
                        for(int j=0;j<interested.length();j++){
                            if(interested.codePointAt(j)==0x964){
                                meaning=interested.substring(0,j);
                                meaning=meaning.replaceAll(Character.toString((char)0x09e7),"");//for bengali one
                                meaning = meaning.trim();
                                return meaning;
                            }
                        }

                        return interested;
                    }
                }
                */

        
        //for(int i=0;i<length;i++){
            
            
            
            /*
            if(text.charAt(i)==']'){
                String[] str = text.split("]");
                if(str.length <2)
                    break;
                else{
                    String interested = str[1];
                    interested = interested.trim();
                    //System.out.println("Interested:"+interested);
                    interested=interested.substring(interested.indexOf(0x020),interested.length());
                    interested=interested.trim();
                    for(int j=0;j<interested.length();j++){
                        if(interested.codePointAt(j)==0x964){
                            meaning=interested.substring(0,j);
                            meaning=meaning.replaceAll(Character.toString((char)0x09e7),"");//for bengali one
                            meaning = meaning.trim();
                            return meaning;
                        }
                    }
                    
                    return interested;
                }
            }*/