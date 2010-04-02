package edu.ucdenver.ccp.parser.ncbi.gene;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import edu.ucdenver.ccp.util.collections.CollectionsUtil;
import edu.ucdenver.ccp.util.test.TestUtil;

public class GenerifsBasicFileParserTest {

	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	private File generifsBasicFile;
	private File generifsBasicFileWithInvalidDateFormat;

	@Before
	public void setUp() throws Exception {
		populateSampleGenerifsBasicFile();
		populateSampleGenerifsBasicFileWithInvalidDateFormat();
	}

	private void populateSampleGenerifsBasicFileWithInvalidDateFormat() throws IOException {
		List<String> lines = CollectionsUtil.createList(
				"#Tax ID\tGene ID\tPubMed ID (PMID) list\tlast update timestamp\tGeneRIF text",
				"9606\t7040\t12858451,1234567\t03-13-2008 \tObservational study of gene-disease.");
		generifsBasicFileWithInvalidDateFormat = TestUtil.populateTestFile(folder, "gb_with_invalid_date", lines);
	}

	private void populateSampleGenerifsBasicFile() throws IOException {
		List<String> lines = CollectionsUtil.createList(
				"#Tax ID\tGene ID\tPubMed ID (PMID) list\tlast update timestamp\tGeneRIF text",
				"34\t4126706\t16689796\t2010-01-21 00:00\tData demonstrate that MrpC binds to "
						+ "at least eight sites in the upstream region of its promoter, and suggest a mechanism "
						+ "by which MrpC and FruA are regulated during the M. xanthus life cycle [MrpC]",
				"9606\t7040\t12858451,1234567\t2008-03-13 08:51\tObservational study of gene-disease "
						+ "association. (HuGE Navigator)");
		generifsBasicFile = TestUtil.populateTestFile(folder, "generifs_basic", lines);
	}

	private GeneRIF getExpectedGeneRif1() throws ParseException {
		return new GeneRIF(34, 4126706, new int[] { 16689796 }, GenerifsBasicFileParser.getGeneRifDateFormat().parse(
				"2010-01-21 00:00"),
				"Data demonstrate that MrpC binds to at least eight sites in the upstream region of its "
						+ "promoter, and suggest a mechanism by which MrpC and FruA are regulated during the M. "
						+ "xanthus life cycle [MrpC]");
	}

	private GeneRIF getExpectedGeneRif2() throws ParseException {
		return new GeneRIF(9606, 7040, new int[] { 12858451, 1234567 }, GenerifsBasicFileParser.getGeneRifDateFormat()
				.parse("2008-03-13 08:51"), "Observational study of gene-disease association. (HuGE Navigator)");
	}

	@Test
	public void testParse() throws Exception {
		Iterator<GeneRIF> generifIter = GenerifsBasicFileParser.parse(generifsBasicFile);
		assertTrue(String.format("Should be 1st of two GeneRIFS."), generifIter.hasNext());
		checkGeneRif(getExpectedGeneRif1(), generifIter.next());
		assertTrue(String.format("Should be 2nd of two GeneRIFS."), generifIter.hasNext());
		checkGeneRif(getExpectedGeneRif2(), generifIter.next());
		assertFalse(generifIter.hasNext());
	}

	private void checkGeneRif(GeneRIF expectedGeneRif, GeneRIF observedGeneRif) {
		assertEquals("Taxonomy IDs must be equal.", expectedGeneRif.getTaxID(), observedGeneRif.getTaxID());
		assertEquals("Entrez Gene IDs must be equal.", expectedGeneRif.getEntrezID(), observedGeneRif.getEntrezID());
		assertArrayEquals("PubMed IDs must be equal.", expectedGeneRif.getPMIDs(), observedGeneRif.getPMIDs());
		assertEquals("Dates must be equal.", expectedGeneRif.getTimeStamp(), observedGeneRif.getTimeStamp());
		assertEquals("Texts must be equal.", expectedGeneRif.getText(), observedGeneRif.getText());
		assertEquals("Ids must be equal.", expectedGeneRif.getID(), observedGeneRif.getID());
		assertEquals("Default ID should be -1.", -1, expectedGeneRif.getID());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testParseWhenInvalidDateExists() throws Exception {
		Iterator<GeneRIF> generifIter = GenerifsBasicFileParser.parse(generifsBasicFileWithInvalidDateFormat);
		assertTrue(generifIter.hasNext());
		GeneRIF g = generifIter.next();
		System.out.println(g.getTimeStamp());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testUnsupportedOperationExceptionWhenCallingRemove() throws Exception {
		Iterator<GeneRIF> generifIter = GenerifsBasicFileParser.parse(generifsBasicFileWithInvalidDateFormat);
		generifIter.remove();
	}

}
