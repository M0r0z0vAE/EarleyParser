import java.util.ArrayList;
import java.io.FileNotFoundException;

public class Earley {
    private String file_path;

    public Earley(String filenameStr){
        file_path = filenameStr;
    }

    public boolean algorithm() throws FileNotFoundException {
        ArrayList<String> fileData = getData(file_path);
        String nonterminal = "";
        String start_sym = "";

        if(fileData != null){
            ArrayList<ArrayList<Rule>> S = new ArrayList<ArrayList<Rule>>();

            String expression = fileData.get(0).replaceAll(" ", "");
            fileData.remove(0);

            System.out.println(expression);
            System.out.println();

            for(int i=0; i<=expression.length();++i) S.add(new ArrayList<Rule>());

            String symbl = fileData.get(0).substring(0, fileData.get(0).indexOf(" "));
            for(int j=0; j<fileData.size();++j){
                String line = fileData.get(j);
                String new_symbl = line.substring(0, line.indexOf(" "));
                if(symbl.equals(new_symbl)){
                    Rule new_rule = new Rule(line);
                    if (!inList(S.get(0), new_rule)) S.get(0).add(new_rule);
                }
                if (!nonterminal.contains(new_symbl)) nonterminal = nonterminal.concat(new_symbl);
                if (j == 0) start_sym = new_symbl;
            }

            for(int k=0; k <= expression.length(); ++k){
                for(int j=0; j<S.get(k).size(); ++j){
                    Rule rule = S.get(k).get(j);
                    if (!rule.finished()){
                        String next_elem = rule.nextElem();
                        if(nonterminal.contains(next_elem)){
                            for(int s=0; s<fileData.size();++s){
                                String line = fileData.get(s);
                                if (line.substring(0, line.indexOf(" ")).equals(next_elem)){
                                    Rule new_rule = new Rule(line, k);
                                    if (!inList(S.get(k), new_rule)) S.get(k).add(new_rule);
                                }
                            }
                        }
                        else{
                            if ((k < expression.length()) && (next_elem.equals(String.valueOf(expression.charAt(k))))){
                                Rule new_rule = new Rule(rule.getRule(), rule.get_pointer(), rule.get_parent());
                                if (!inList(S.get(k+1), new_rule)) S.get(k+1).add(new_rule);
                            }
                        }
                    }
                    else{
                        symbl = rule.getRule().substring(0, rule.getRule().indexOf(" "));;
                        for(int s=S.get(rule.get_parent()).size()-1; s>=0;--s){
                            Rule n_rule = S.get(rule.get_parent()).get(s);
                            if(n_rule.finished()) continue;
                            if((n_rule.getRule().substring(n_rule.get_pointer()+1).contains(symbl)) && (symbl.equals(n_rule.nextElem()))){
                                Rule new_rule = new Rule(n_rule.getRule(), n_rule.get_pointer(), n_rule.get_parent());
                                if (!inList(S.get(k), new_rule)) S.get(k).add(new_rule);
                            }
                        }
                    }
                }
            }

            for(int k=0; k < S.size(); ++k){
                System.out.println(expression.substring(0, k)+"`"+expression.substring(k));
                System.out.println("k = "+k);
                for(int j=0; j < S.get(k).size(); ++j){
                    System.out.println(S.get(k).get(j).print_rule()+"  ||: "+S.get(k).get(j).get_parent());
                }
                System.out.println();
            }

            for(int j=0; j < S.get(S.size()-1).size(); ++j){
                Rule rl = S.get(S.size()-1).get(j);
                String str = rl.getRule();
                if ((rl.get_parent() == 0) && (rl.finished()) && (str.substring(0, str.indexOf(" ")).equals(start_sym))) return true;
            }
        }
        return false;
    }

    private ArrayList<String> getData(String filename) throws FileNotFoundException {
        RFile new_file = new RFile(filename);
        ArrayList<String> Data = new_file.OpenAndRead();
        return Data;
    }

    private boolean inList(ArrayList<Rule> list, Rule rl){
        for(int i=0; i<list.size();++i){
            Rule old = list.get(i);
            if((rl.print_rule().equals(old.print_rule())) && (rl.get_parent() == old.get_parent())) return true;
        }
        return false;
    }
}