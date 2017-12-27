
import lambda.Lang;
import lambda.PType;
import learn.DataSet;
import learn.Train;
import parser.Lexicon;
import parser.LexiconFeatSet;
import parser.Parser;

public class TestTrain extends Train {

	public static void main(String[] args) {

		PType.addTypesFromFile("geo-funql.types");
		Lang.loadLangFromFile("geo-funql.lang");

		Train.emptyTest = true;
		String lang = args[1];
		DataSet train = new DataSet("data/geo880-funql-" + lang + ".train");
		DataSet test = new DataSet("data/geo880-funql-" + lang + ".test");

		System.out.println("Train Size: " + train.size());
		System.out.println("Test Size: " + test.size());

		LexiconFeatSet.loadCoOccCounts("data/geo-funql." + lang + ".giza_probs");

		String fixedlex = args[0];

		Lexicon fixed = new Lexicon();
		if (!fixedlex.equals("none")) {
			fixed.addEntriesFromFile(fixedlex);
		}

		Train.EPOCHS = 20;

		Train.alpha_0 = 1.0;
		Train.c = 0.00001;
		Train.verbose = true;
		Train.maxSentLen = 50;

		LexiconFeatSet.initWeightMultiplier = 10.0;
		LexiconFeatSet.initLexWeight = 10.0;

		Parser.pruneN = 200;

		System.out.println("alpha_0 = " + Train.alpha_0);
		System.out.println("C = " + Train.c);
		System.out.println("initialMultiplier = " + LexiconFeatSet.initWeightMultiplier);
		System.out.println("NP init = " + LexiconFeatSet.initLexWeight);
		System.out.println("Parser beam  = " + Parser.pruneN);

		Train t = new Train();
		t.setDataSet(train);
		t.setTestSet(test);

		Parser p = new Parser(fixed);
		p.makeFeatures();

		t.stocGradTrain(p, true);

		t.test(p, true);

	}

}
