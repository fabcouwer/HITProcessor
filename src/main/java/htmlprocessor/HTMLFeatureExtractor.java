package htmlprocessor;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

//Extracts statistics from HTML Documents
public class HTMLFeatureExtractor {

	// Returns the word count of the body text of doc
	public int getWordCount(Document doc) {
		String text = doc.body().text();
		return text.split(" ").length;
	}

	// Returns the amount of hyperlinks in doc
	public int getLinkCount(Document doc) {
		return doc.select("a[href]").size();
	}

	// Returns the percentage of body text in doc that is emphasized
	// (<b>, <strong>, <i>, <emph>)
	public Double getEmphBodyTextPct(Document doc) {
		// Get total word count
		String text = doc.body().text();
		double totalWords = 0d;
		totalWords += text.split(" ").length;

		// Get character count in emphasized tags
		double emphWords = 0d;
		Elements els = doc.body().select("b,strong,i,emph");
		for (Element e : els) {
			emphWords += e.text().split(" ").length;
		}

		return new Double(emphWords / totalWords);
	}

	// Returns the percentage of body text relative to display (headers etc..)
	public double getBodyTextPct(Document doc) {
		// Get total word count
		String text = doc.body().text();
		double totalWords = 0d;
		totalWords += text.split(" ").length;

		// Get word count in header tags
		double headersWords = 0d;
		Elements els = doc.body().select("h1,h2,h3,h4,h5,h6");
		for (Element e : els) {
			headersWords += e.text().split(" ").length;
		}

		return new Double((totalWords - headersWords) / totalWords);
	}

	// Returns the amount of script files in doc
	public int getScriptCount(Document doc) {
		return doc.select("script[src]").size();
	}

	// Returns the amount of stylesheets in doc
	public int getStyleSheetCount(Document doc) {
		return doc.select("link[type=text/css], link[rel=stylesheet").size();
	}

	// Returns the amount of images in doc
	public int getImageCount(Document doc) {
		return doc.getElementsByTag("img").size();
	}

	// Returns the amount of input elements (<input>, <button>, amount of
	// options in <select>) in doc
	public int getInputCount(Document doc) {
		int result = 0;

		result += doc.getElementsByTag("input").size();
		result += doc.getElementsByTag("button").size();

		for (Element e : doc.getElementsByTag("select")) {
			result += e.getElementsByTag("option").size();
		}

		return result;
	}

}
