public class Rule {
    private String rule;
    private int pointer;
    private int parent;

    public Rule(String rl){
        rule = rl;
        pointer = rl.indexOf(" ") + 3;
        parent = 0;
    }

    public Rule(String rl, int prnt){
        rule = rl;
        pointer = rl.indexOf(" ") + 3;
        parent = prnt;
    }

    public Rule(String rl, int pt, int prnt){
        rule = rl;
        pointer = pt+1;
        parent = prnt;
    }

    public String getRule(){
        return rule;
    }

    public void set_pointer(int pt){
        pointer = pt;
    }

    public int get_pointer(){
        return pointer;
    }

    public int get_parent(){
        return parent;
    }

    public boolean finished(){
        if(pointer == rule.length()-1) return true;
        return false;
    }

    public String nextElem(){
        String rez = rule.substring(pointer+1, pointer+2);
        if (rez.equals(" ")) rez = rule.substring(pointer+2, pointer+3);
        return rez;
    }

    public String print_rule(){
        return rule.substring(0, pointer+1)+"`"+rule.substring(pointer+1);
    }
}