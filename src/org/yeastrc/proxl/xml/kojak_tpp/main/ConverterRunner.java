package org.yeastrc.proxl.xml.kojak_tpp.main;

import java.io.File;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;
import java.util.Vector;

import org.yeastrc.proxl.xml.kojak_tpp.builder.XMLBuilder;
import org.yeastrc.proxl.xml.kojak_tpp.objects.IProphetReportedPeptide;
import org.yeastrc.proxl.xml.kojak_tpp.objects.IProphetResult;
import org.yeastrc.proxl.xml.kojak_tpp.reader.TPPAnalysis;
import org.yeastrc.proxl.xml.kojak_tpp.reader.TPPResultsParser;
import org.yeastrc.proxl.xml.kojak_tpp.reader.KojakConfReader;
import org.yeastrc.proxl.xml.kojak_tpp.reader.TPPErrorAnalysis;
import org.yeastrc.proxl.xml.kojak_tpp.reader.TPPErrorAnalyzer;

public class ConverterRunner {

	// quickly get a new instance of this class
	public static ConverterRunner createInstance() { return new ConverterRunner(); }

	public void runConversion( String pepXMLFilePath,
            String outFilePath,
            String fastaFilePath,
            Vector<String> kojakConfFilePaths,
            String linkerName,
            Vector<String> decoyIdentifiers,
            BigDecimal importCutoff
           ) throws Exception {
		
		
		///////// Set up config parameters ////////////////////
		
		System.err.print( "Loading configuration..." );
		
		TPPAnalysis analysis = TPPAnalysis.loadAnalysis( pepXMLFilePath );
		
		analysis.setDecoyIdentifiers( decoyIdentifiers );
		analysis.setKojakConfReader( KojakConfReader.getInstance( kojakConfFilePaths.get( 0 ) ) );
		analysis.setFastaFile( new File( fastaFilePath ) );
		analysis.setKojakConfFilePaths( kojakConfFilePaths );
		analysis.setLinkerName( linkerName );
		if( importCutoff != null )
			analysis.setImportFilter( importCutoff );

		System.err.println( "Done." );
		
		
		
		/////////////// Read in the data ////////////////////
		
		System.err.print( "Reading data in from pep XML..." );
		
		// parse the data from the pepXML into a java data structure suitable for writing as ProXL XML
		Map<IProphetReportedPeptide, Collection<IProphetResult>> resultsByReportedPeptide = 
				TPPResultsParser.getInstance().getResultsFromAnalysis( analysis );
		
		System.err.println( "Done." );

		
		
		/////////////////// Perform FDR analysis ////////////////////////
		
		System.err.print( "Performing FDR analysis of iProphet data..." );

		TPPErrorAnalysis errorAnalysis = TPPErrorAnalyzer.performPeptideProphetAnalysis( resultsByReportedPeptide );		
		
		System.err.println( "Done." );
		
		
		/////////////////////// Write out XML //////////////////////
		
		System.err.print( "Writing out XML" );

		XMLBuilder builder = new XMLBuilder();
		builder.buildAndSaveXML(analysis, resultsByReportedPeptide, errorAnalysis, new File( outFilePath ) );
		
		System.err.println( "Done." );
		
	}
	
	
}
