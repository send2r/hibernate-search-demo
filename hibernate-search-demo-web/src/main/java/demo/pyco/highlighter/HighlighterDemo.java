package demo.pyco.highlighter;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.Scorer;

public class HighlighterDemo {

	public static void main(String[] args) {
		String text = "The quick brown fox jumps over the lazy dog";
		TermQuery query = new TermQuery(new Term("field", "fox"));
		Scorer scorer = new QueryScorer(query);
		Highlighter highlighter = new Highlighter(scorer);
		TokenStream tokenStream = new SimpleAnalyzer().tokenStream("field",new StringReader(text));
		try {
			System.out.println(highlighter.getBestFragment(tokenStream, text));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
