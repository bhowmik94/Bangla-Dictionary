package dictionary;
/**
 *
 * @author SUJAN
 */
public class PoS {
    String pos = "";
    String text = "";
    int length;
    public PoS(String tx, int l){
        text = tx;
        length = l;
    }
    String posFinding(){
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
                            pos = interested.substring(0,interested.indexOf(0x020));
                        else
                            pos="";
                        
                        pos = pos.replaceAll(Character.toString((char)0x02c),"");//for comma
                        pos = pos.replaceAll(Character.toString((char)0x03b),"");//for semi colon
                        pos = pos.replaceAll(Character.toString((char)0x09e7),"");//for bengali one
                        pos = pos.replaceAll(Character.toString((char)0x09e8),"");//for bengali two
                        pos = pos.trim();
                        break;
                    }
                }
            }
        }
        return pos;
    }
}


/*
                else if(text.charAt(i)==']' && occurance==2){
                    String tmp = text.substring(pos2+1,text.length());
                    tmp = tmp.trim();
                    //String[] str = text.split("]");
                    if(tmp.length()<2)
                        break;
                    else{
                        String interested = tmp;
                        //interested = interested.trim();
                        pos = interested.substring(0,interested.indexOf(0x020));
                        pos = pos.replaceAll(Character.toString((char)0x02c),"");//for comma
                        pos = pos.replaceAll(Character.toString((char)0x03b),"");//for semi colon
                        pos = pos.replaceAll(Character.toString((char)0x09e7),"");//for bengali one
                        pos = pos.replaceAll(Character.toString((char)0x09e8),"");//for bengali two
                        pos = pos.trim();
                        break;
                    }
                }
                */
                //else if(text.charAt(i)==']' && --occurance==0)