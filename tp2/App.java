package mytweetyapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.tweetyproject.commons.ParserException;
import org.tweetyproject.logics.commons.syntax.Constant;
import org.tweetyproject.logics.commons.syntax.Predicate;
import org.tweetyproject.logics.commons.syntax.Sort;
import org.tweetyproject.logics.fol.parser.FolParser;
import org.tweetyproject.logics.fol.reasoner.FolReasoner;
import org.tweetyproject.logics.fol.reasoner.SimpleFolReasoner;
import org.tweetyproject.logics.fol.syntax.FolBeliefSet;
import org.tweetyproject.logics.fol.syntax.FolFormula;
import org.tweetyproject.logics.fol.syntax.FolSignature;

public class App {

    public static void main(String[] args) throws ParserException, IOException {
        FolSignature sig = new FolSignature(true);
        Sort sortAvenger = new Sort("avenger");
        sig.add(sortAvenger);

        //   constantes qui sont les noms des héros
        Constant ironMan = new Constant("ironMan", sortAvenger);
        Constant captainAmerica = new Constant("captainAmerica", sortAvenger);
        Constant mayParker = new Constant("mayParker", sortAvenger);
        Constant blackWidow = new Constant("blackWidow", sortAvenger);
        //Constant hawkeye = new Constant("hawkeye", sortAvenger);
        sig.add(ironMan, captainAmerica, blackWidow, mayParker);

        // prédicats
        List<Sort> plist = new ArrayList<>();
        plist.add(sortAvenger);
        Predicate avenger = new Predicate("avenger", plist);
        Predicate hero = new Predicate("hero", plist);
        Predicate humain = new Predicate("humain", plist);
        Predicate flies = new Predicate("flies", plist);
        Predicate foughtInEndgame = new Predicate("foughtInEndgame", plist);
        Predicate diedInEndgame = new Predicate("diedInEndgame", plist);
        sig.add(avenger, hero, humain, flies, foughtInEndgame, diedInEndgame);

        FolParser parser = new FolParser();
        parser.setSignature(sig);

        // base de croyances
        FolBeliefSet bs = new FolBeliefSet();
        bs.add((FolFormula) parser.parseFormula("avenger(ironMan)"));
        //bs.add((FolFormula) parser.parseFormula("avenger(captainAmerica)"));
        bs.add((FolFormula) parser.parseFormula("humain(mayParker)"));
        //bs.add((FolFormula) parser.parseFormula("avenger(blackWidow)"));
        //bs.add((FolFormula) parser.parseFormula("avenger(hawkeye)"));
        bs.add((FolFormula) parser.parseFormula("flies(ironMan)"));
        bs.add((FolFormula) parser.parseFormula("foughtInEndgame(ironMan)"));
       // bs.add((FolFormula) parser.parseFormula("foughtInEndgame(captainAmerica)"));
        bs.add((FolFormula) parser.parseFormula("foughtInEndgame(blackWidow)"));
       // bs.add((FolFormula) parser.parseFormula("foughtInEndgame(hawkeye)"));
        //bs.add((FolFormula) parser.parseFormula("diedInEndgame(ironMan)"));
        bs.add((FolFormula) parser.parseFormula("diedInEndgame(blackWidow)"));
        
        System.out.println("Base de croyances: " + bs);

        FolReasoner reasoner = new SimpleFolReasoner();

        // queries
        FolFormula query1 = (FolFormula) parser.parseFormula("hero(mayParker)");
        FolFormula query3 = (FolFormula) parser.parseFormula("foughtInEndgame(ironMan)");
        FolFormula query4 = (FolFormula) parser.parseFormula("diedInEndgame(blackWidow)");
        FolFormula query5 = (FolFormula) parser.parseFormula("flies(captainAmerica)");
        FolFormula query6 = (FolFormula) parser.parseFormula("diedInEndgame(ironMan)");

        boolean result1 = reasoner.query(bs, query1);
        boolean result3 = reasoner.query(bs, query3);
        boolean result4 = reasoner.query(bs, query4);
        boolean result5 = reasoner.query(bs, query5);
        boolean result6 = reasoner.query(bs, query6);

        System.out.println("Is mayParker a hero? " + result1);
        System.out.println("Did ironMan fight in Endgame? " + result3);
        System.out.println("Did blackWidow die in Endgame? " + result4);
        System.out.println("Can captainAmerica fly? " + result5);
        System.out.println("Did ironMan die in Endgame? " + result6);
    }
}
