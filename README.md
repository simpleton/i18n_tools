i18n_tools
==========
## JavaParser ##
just the fork repo from [http://code.google.com/p/javaparser/](http://code.google.com/p/javaparser/)


## AIAS ##
AIAS is short for Auto Internationalization Android String. It use Abstarct Syntax Tree for externalize StringLiteral, ane change the source code automatically.

Good Luck~

* * * * *
## How to Use ##
```
	public static void main(final String[] args) throws ParseException, IOException, ParserConfigurationException, TransformerException, SAXException {
		IOutputHelper out_helper = new XmlHelper.Builder().setDebug(false).setFilePath("out/string.xml").build();
		StringParser parser = new StringParser.Builder().setDebug(true)	.setInputFolder("test/").setOutputFolder("out/").setKeyMakerHelper(new pinyinHelper()).setOutputClient(out_helper).build();
		parser.run();
	}
```
# TODO:#

* * * * *

1. add log file for tracing modifies
