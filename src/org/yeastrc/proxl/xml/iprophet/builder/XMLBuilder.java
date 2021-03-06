package org.yeastrc.proxl.xml.iprophet.builder;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

import org.yeastrc.fasta.FASTAEntry;
import org.yeastrc.fasta.FASTAHeader;
import org.yeastrc.fasta.FASTAReader;
import org.yeastrc.proxl.xml.iprophet.annotations.PSMAnnotationTypes;
import org.yeastrc.proxl.xml.iprophet.annotations.PSMDefaultVisibleAnnotationTypes;
import org.yeastrc.proxl.xml.iprophet.constants.IProphetConstants;
import org.yeastrc.proxl.xml.iprophet.objects.IProphetReportedPeptide;
import org.yeastrc.proxl.xml.iprophet.objects.IProphetResult;
import org.yeastrc.proxl.xml.iprophet.reader.IProphetAnalysis;
import org.yeastrc.proxl.xml.iprophet.reader.IProphetProteinNameCollector;
import org.yeastrc.proxl.xml.iprophet.reader.TPPErrorAnalysis;
import org.yeastrc.proxl.xml.iprophet.utils.ModUtils;
import org.yeastrc.proxl.xml.iprophet.utils.PepXMLUtils;
import org.yeastrc.proxl_import.api.xml_dto.AnnotationCutoffsOnImport;
import org.yeastrc.proxl_import.api.xml_dto.AnnotationSortOrder;
import org.yeastrc.proxl_import.api.xml_dto.ConfigurationFile;
import org.yeastrc.proxl_import.api.xml_dto.ConfigurationFiles;
import org.yeastrc.proxl_import.api.xml_dto.CrosslinkMass;
import org.yeastrc.proxl_import.api.xml_dto.CrosslinkMasses;
import org.yeastrc.proxl_import.api.xml_dto.DecoyLabel;
import org.yeastrc.proxl_import.api.xml_dto.DecoyLabels;
import org.yeastrc.proxl_import.api.xml_dto.DefaultVisibleAnnotations;
import org.yeastrc.proxl_import.api.xml_dto.DescriptivePsmAnnotation;
import org.yeastrc.proxl_import.api.xml_dto.DescriptivePsmAnnotationTypes;
import org.yeastrc.proxl_import.api.xml_dto.DescriptivePsmAnnotations;
import org.yeastrc.proxl_import.api.xml_dto.FilterablePsmAnnotation;
import org.yeastrc.proxl_import.api.xml_dto.FilterablePsmAnnotationTypes;
import org.yeastrc.proxl_import.api.xml_dto.FilterablePsmAnnotations;
import org.yeastrc.proxl_import.api.xml_dto.LinkType;
import org.yeastrc.proxl_import.api.xml_dto.LinkedPosition;
import org.yeastrc.proxl_import.api.xml_dto.LinkedPositions;
import org.yeastrc.proxl_import.api.xml_dto.Linker;
import org.yeastrc.proxl_import.api.xml_dto.Linkers;
import org.yeastrc.proxl_import.api.xml_dto.MatchedProteins;
import org.yeastrc.proxl_import.api.xml_dto.Modification;
import org.yeastrc.proxl_import.api.xml_dto.Modifications;
import org.yeastrc.proxl_import.api.xml_dto.Peptide;
import org.yeastrc.proxl_import.api.xml_dto.Peptides;
import org.yeastrc.proxl_import.api.xml_dto.Protein;
import org.yeastrc.proxl_import.api.xml_dto.ProteinAnnotation;
import org.yeastrc.proxl_import.api.xml_dto.ProxlInput;
import org.yeastrc.proxl_import.api.xml_dto.Psm;
import org.yeastrc.proxl_import.api.xml_dto.PsmAnnotationCutoffsOnImport;
import org.yeastrc.proxl_import.api.xml_dto.PsmAnnotationSortOrder;
import org.yeastrc.proxl_import.api.xml_dto.Psms;
import org.yeastrc.proxl_import.api.xml_dto.ReportedPeptide;
import org.yeastrc.proxl_import.api.xml_dto.ReportedPeptides;
import org.yeastrc.proxl_import.api.xml_dto.SearchAnnotationCutoff;
import org.yeastrc.proxl_import.api.xml_dto.SearchProgram;
import org.yeastrc.proxl_import.api.xml_dto.SearchProgram.PsmAnnotationTypes;
import org.yeastrc.proxl_import.api.xml_dto.SearchProgramInfo;
import org.yeastrc.proxl_import.api.xml_dto.SearchPrograms;
import org.yeastrc.proxl_import.api.xml_dto.StaticModification;
import org.yeastrc.proxl_import.api.xml_dto.StaticModifications;
import org.yeastrc.proxl_import.api.xml_dto.VisiblePsmAnnotations;
import org.yeastrc.proxl_import.create_import_file_from_java_objects.main.CreateImportFileFromJavaObjectsMain;
import org.yeastrc.taxonomy.main.GetTaxonomyId;

/**
 * Take the populated pLink objects, convert to XML and write the XML file
 * 
 * @author Michael Riffle
 * @date Jul 18, 2016
 *
 */
public class XMLBuilder {

	/**
	 * Build and save the XML document based on the analysis results.
	 *
	 * @param analysis
	 * @param resultsByReportedPeptide
	 * @param errorAnalysis
	 * @param outfile
	 * @throws Exception
	 */
	public void buildAndSaveXML(
								 IProphetAnalysis analysis,
								 Map<IProphetReportedPeptide, Collection<IProphetResult>> resultsByReportedPeptide,
								 TPPErrorAnalysis errorAnalysis,
			                     File outfile
			                    ) throws Exception {

		
		File fastaFile = analysis.getFastaFile();
		
		// root node of the XML
		ProxlInput proxlInputRoot = new ProxlInput();

		proxlInputRoot.setFastaFilename( analysis.getFASTADatabase() );
		
		SearchProgramInfo searchProgramInfo = new SearchProgramInfo();
		proxlInputRoot.setSearchProgramInfo( searchProgramInfo );
		
		//
		// Define the sort order
		//
		AnnotationSortOrder annotationSortOrder = new AnnotationSortOrder();
		searchProgramInfo.setAnnotationSortOrder( annotationSortOrder );
		
		PsmAnnotationSortOrder psmAnnotationSortOrder = new PsmAnnotationSortOrder();
		annotationSortOrder.setPsmAnnotationSortOrder( psmAnnotationSortOrder );
		
		psmAnnotationSortOrder.getSearchAnnotation().addAll( org.yeastrc.proxl.xml.iprophet.annotations.PSMAnnotationTypeSortOrder.getPSMAnnotationTypeSortOrder() );
		
		
		SearchPrograms searchPrograms = new SearchPrograms();
		searchProgramInfo.setSearchPrograms( searchPrograms );
		
		SearchProgram searchProgram = new SearchProgram();
		searchPrograms.getSearchProgram().add( searchProgram );
		
		// add interprophet
		searchProgram.setName( IProphetConstants.SEARCH_PROGRAM_NAME_IPROPHET );
		searchProgram.setDisplayName( IProphetConstants.SEARCH_PROGRAM_NAME_IPROPHET );
		searchProgram.setVersion( PepXMLUtils.getTPPVersion( analysis ) );

		{
			PsmAnnotationTypes psmAnnotationTypes = new PsmAnnotationTypes();
			searchProgram.setPsmAnnotationTypes( psmAnnotationTypes );
			
			FilterablePsmAnnotationTypes filterablePsmAnnotationTypes = new FilterablePsmAnnotationTypes();
			psmAnnotationTypes.setFilterablePsmAnnotationTypes( filterablePsmAnnotationTypes );
			filterablePsmAnnotationTypes.getFilterablePsmAnnotationType().addAll( PSMAnnotationTypes.getFilterablePsmAnnotationTypes( IProphetConstants.SEARCH_PROGRAM_NAME_IPROPHET ) );
			
		}
		
		//
		// Define which annotation types are visible by default
		//
		DefaultVisibleAnnotations xmlDefaultVisibleAnnotations = new DefaultVisibleAnnotations();
		searchProgramInfo.setDefaultVisibleAnnotations( xmlDefaultVisibleAnnotations );
		
		VisiblePsmAnnotations xmlVisiblePsmAnnotations = new VisiblePsmAnnotations();
		xmlDefaultVisibleAnnotations.setVisiblePsmAnnotations( xmlVisiblePsmAnnotations );

		xmlVisiblePsmAnnotations.getSearchAnnotation().addAll( PSMDefaultVisibleAnnotationTypes.getDefaultVisibleAnnotationTypes() );
		
		
		// add peptideprophet
		searchProgram = new SearchProgram();
		searchPrograms.getSearchProgram().add( searchProgram );
		
		searchProgram.setName( IProphetConstants.SEARCH_PROGRAM_NAME_PPROPHET );
		searchProgram.setDisplayName( IProphetConstants.SEARCH_PROGRAM_NAME_PPROPHET  );
		searchProgram.setVersion( PepXMLUtils.getTPPVersion( analysis ) );
		
		{
			PsmAnnotationTypes psmAnnotationTypes = new PsmAnnotationTypes();
			searchProgram.setPsmAnnotationTypes( psmAnnotationTypes );
			
			FilterablePsmAnnotationTypes filterablePsmAnnotationTypes = new FilterablePsmAnnotationTypes();
			psmAnnotationTypes.setFilterablePsmAnnotationTypes( filterablePsmAnnotationTypes );
			filterablePsmAnnotationTypes.getFilterablePsmAnnotationType().addAll( PSMAnnotationTypes.getFilterablePsmAnnotationTypes( IProphetConstants.SEARCH_PROGRAM_NAME_PPROPHET ) );
			
		}
		
		
		// add kojak
		searchProgram = new SearchProgram();
		searchPrograms.getSearchProgram().add( searchProgram );
		
		searchProgram.setName( IProphetConstants.SEARCH_PROGRAM_NAME_KOJAK );
		searchProgram.setDisplayName( IProphetConstants.SEARCH_PROGRAM_NAME_KOJAK  );
		searchProgram.setVersion( PepXMLUtils.getKojakVersionFromXML( analysis.getAnalysis() ) );
		
		{
			PsmAnnotationTypes psmAnnotationTypes = new PsmAnnotationTypes();
			searchProgram.setPsmAnnotationTypes( psmAnnotationTypes );
			
			FilterablePsmAnnotationTypes filterablePsmAnnotationTypes = new FilterablePsmAnnotationTypes();
			psmAnnotationTypes.setFilterablePsmAnnotationTypes( filterablePsmAnnotationTypes );
			filterablePsmAnnotationTypes.getFilterablePsmAnnotationType().addAll( PSMAnnotationTypes.getFilterablePsmAnnotationTypes( IProphetConstants.SEARCH_PROGRAM_NAME_KOJAK ) );
			
			DescriptivePsmAnnotationTypes descriptivePsmAnnotationTypes = new DescriptivePsmAnnotationTypes();
			psmAnnotationTypes.setDescriptivePsmAnnotationTypes( descriptivePsmAnnotationTypes );
			descriptivePsmAnnotationTypes.getDescriptivePsmAnnotationType().addAll( PSMAnnotationTypes.getDescriptivePsmAnnotationTypes( IProphetConstants.SEARCH_PROGRAM_NAME_KOJAK ) );
		}
		
		
		/*
		 * Define the default import cutoffs
		 */
		if( analysis.getImportFilter() != null  && analysis.getImportFilter().floatValue() < 1.0 ) {
			AnnotationCutoffsOnImport annotationCutoffsOnImport = new AnnotationCutoffsOnImport();
			searchProgramInfo.setAnnotationCutoffsOnImport( annotationCutoffsOnImport );
			
			PsmAnnotationCutoffsOnImport psmAnnotationCutoffsOnImport = new PsmAnnotationCutoffsOnImport();
			annotationCutoffsOnImport.setPsmAnnotationCutoffsOnImport( psmAnnotationCutoffsOnImport );
			
			SearchAnnotationCutoff searchAnnotationCutoff = new SearchAnnotationCutoff();
			searchAnnotationCutoff.setAnnotationName( PSMAnnotationTypes.IPROPHET_ANNOTATION_TYPE_ERROR );
			searchAnnotationCutoff.setSearchProgram( IProphetConstants.SEARCH_PROGRAM_NAME_IPROPHET );
			searchAnnotationCutoff.setCutoffValue( analysis.getImportFilter() );
			
			psmAnnotationCutoffsOnImport.getSearchAnnotationCutoff().add( searchAnnotationCutoff );
		}
		
		//
		// Define the linker information
		//
		Linkers linkers = new Linkers();
		proxlInputRoot.setLinkers( linkers );

		Linker linker = new Linker();
		linkers.getLinker().add( linker );
		
		linker.setName( analysis.getLinkerName() );
		
		CrosslinkMasses masses = new CrosslinkMasses();
		linker.setCrosslinkMasses( masses );
		
		for( BigDecimal mass : analysis.getKojakConfReader().getCrosslinkMasses() ) {
			CrosslinkMass xlinkMass = new CrosslinkMass();
			linker.getCrosslinkMasses().getCrosslinkMass().add( xlinkMass );
			
			// set the mass for this crosslinker to the calculated mass for the crosslinker, as defined in the properties file
			xlinkMass.setMass( mass );
		}
		
		//
		// Define the static mods
		//
		StaticModifications smods = new StaticModifications();
		proxlInputRoot.setStaticModifications( smods );
		
		for( String moddedResidue : analysis.getKojakConfReader().getStaticModifications().keySet() ) {
				
				StaticModification xmlSmod = new StaticModification();
				xmlSmod.setAminoAcid( moddedResidue );
				xmlSmod.setMassChange( analysis.getKojakConfReader().getStaticModifications().get( moddedResidue ) );
				
				smods.getStaticModification().add( xmlSmod );
		}
		
		//
		// Add decoy labels (optional)
		//
		DecoyLabels xmlDecoyLabels = new DecoyLabels();
		proxlInputRoot.setDecoyLabels( xmlDecoyLabels );
		
		
		if( analysis.getDecoyIdentifiers() != null ) {
			for( String decoyId : analysis.getDecoyIdentifiers() ) {
				DecoyLabel xmlDecoyLabel = new DecoyLabel();
				xmlDecoyLabels.getDecoyLabel().add( xmlDecoyLabel );				
				xmlDecoyLabel.setPrefix( decoyId);
			}
		}
		
		//
		// Define the peptide and PSM data
		//
		ReportedPeptides reportedPeptides = new ReportedPeptides();
		proxlInputRoot.setReportedPeptides( reportedPeptides );
		

		
		// create a unique set of peptides found, to ensure each one is found in at least 
		// one of the reported proteins
		Collection<String> peptides = new HashSet<>();
		
		// iterate over each distinct reported peptide
		for( IProphetReportedPeptide rp : resultsByReportedPeptide.keySet() ) {
			
			peptides.add( rp.getPeptide1().getSequence() );
			if( rp.getPeptide2() != null ) peptides.add( rp.getPeptide2().getSequence() );
			
			ReportedPeptide xmlReportedPeptide = new ReportedPeptide();
			reportedPeptides.getReportedPeptide().add( xmlReportedPeptide );
			
			xmlReportedPeptide.setReportedPeptideString( rp.toString() );
			
			if( rp.getType() == IProphetConstants.LINK_TYPE_CROSSLINK )
				xmlReportedPeptide.setType( LinkType.CROSSLINK );
			else if( rp.getType() == IProphetConstants.LINK_TYPE_LOOPLINK )
				xmlReportedPeptide.setType( LinkType.LOOPLINK );
			else
				xmlReportedPeptide.setType( LinkType.UNLINKED );
			
			Peptides xmlPeptides = new Peptides();
			xmlReportedPeptide.setPeptides( xmlPeptides );
			
			// add in the 1st parsed peptide
			{
				Peptide xmlPeptide = new Peptide();
				xmlPeptides.getPeptide().add( xmlPeptide );
				
				xmlPeptide.setSequence( rp.getPeptide1().getSequence() );
				
				// add in the mods for this peptide
				if( rp.getPeptide1().getModifications() != null && rp.getPeptide1().getModifications().keySet().size() > 0 ) {
					
					Modifications xmlModifications = new Modifications();
					xmlPeptide.setModifications( xmlModifications );
					
					for( int position : rp.getPeptide1().getModifications().keySet() ) {
						for( BigDecimal modMass : rp.getPeptide1().getModifications().get( position ) ) {
	
							Modification xmlModification = new Modification();
							xmlModifications.getModification().add( xmlModification );
							
							xmlModification.setMass( modMass );
							xmlModification.setPosition( new BigInteger( String.valueOf( position ) ) );
							xmlModification.setIsMonolink( ModUtils.isMonolink( modMass, analysis.getKojakConfReader() ) );
							
						}
					}
				}
				
				// add in the linked position(s) in this peptide
				if( rp.getType() == IProphetConstants.LINK_TYPE_CROSSLINK || rp.getType() == IProphetConstants.LINK_TYPE_LOOPLINK ) {
					
					LinkedPositions xmlLinkedPositions = new LinkedPositions();
					xmlPeptide.setLinkedPositions( xmlLinkedPositions );
					
					LinkedPosition xmlLinkedPosition = new LinkedPosition();
					xmlLinkedPositions.getLinkedPosition().add( xmlLinkedPosition );
					xmlLinkedPosition.setPosition( new BigInteger( String.valueOf( rp.getPosition1() ) ) );
					
					if( rp.getType() == IProphetConstants.LINK_TYPE_LOOPLINK ) {
						
						xmlLinkedPosition = new LinkedPosition();
						xmlLinkedPositions.getLinkedPosition().add( xmlLinkedPosition );
						xmlLinkedPosition.setPosition( new BigInteger( String.valueOf( rp.getPosition2() ) ) );
						
					}
				}
				
			}
			
			
			// add in the 2nd parsed peptide, if it exists
			if( rp.getPeptide2() != null ) {
				
				Peptide xmlPeptide = new Peptide();
				xmlPeptides.getPeptide().add( xmlPeptide );
				
				xmlPeptide.setSequence( rp.getPeptide2().getSequence() );
				
				// add in the mods for this peptide
				if( rp.getPeptide2().getModifications() != null && rp.getPeptide2().getModifications().keySet().size() > 0 ) {
					
					Modifications xmlModifications = new Modifications();
					xmlPeptide.setModifications( xmlModifications );
					
					for( int position : rp.getPeptide2().getModifications().keySet() ) {
						for( BigDecimal modMass : rp.getPeptide2().getModifications().get( position ) ) {
							
							Modification xmlModification = new Modification();
							xmlModifications.getModification().add( xmlModification );
							
							xmlModification.setMass( modMass );
							xmlModification.setPosition( new BigInteger( String.valueOf( position ) ) );
							xmlModification.setIsMonolink( ModUtils.isMonolink( modMass, analysis.getKojakConfReader() ) );
							
						}
					}
				}
				
				// add in the linked position in this peptide
				if( rp.getType() == IProphetConstants.LINK_TYPE_CROSSLINK ) {
					
					LinkedPositions xmlLinkedPositions = new LinkedPositions();
					xmlPeptide.setLinkedPositions( xmlLinkedPositions );
					
					LinkedPosition xmlLinkedPosition = new LinkedPosition();
					xmlLinkedPositions.getLinkedPosition().add( xmlLinkedPosition );
					xmlLinkedPosition.setPosition( new BigInteger( String.valueOf( rp.getPosition2() ) ) );
				}
			}
			
			
			// add in the PSMs and annotations
			Psms xmlPsms = new Psms();
			xmlReportedPeptide.setPsms( xmlPsms );
			
			// iterate over all PSMs for this reported peptide
			for( IProphetResult result : resultsByReportedPeptide.get( rp ) ) {
				Psm xmlPsm = new Psm();
				xmlPsms.getPsm().add( xmlPsm );
				
				xmlPsm.setScanNumber( new BigInteger( String.valueOf( result.getScanNumber() ) ) );
				xmlPsm.setPrecursorCharge( new BigInteger( String.valueOf( result.getCharge() ) ) );
				xmlPsm.setScanFileName( result.getScanFile() );
				
				if( rp.getType() == IProphetConstants.LINK_TYPE_CROSSLINK || rp.getType() == IProphetConstants.LINK_TYPE_LOOPLINK )
					xmlPsm.setLinkerMass( result.getLinkerMass() );
				
				// add in the filterable PSM annotations (e.g., score)
				FilterablePsmAnnotations xmlFilterablePsmAnnotations = new FilterablePsmAnnotations();
				xmlPsm.setFilterablePsmAnnotations( xmlFilterablePsmAnnotations );
				
				// handle iprophet error
				{
					FilterablePsmAnnotation xmlFilterablePsmAnnotation = new FilterablePsmAnnotation();
					xmlFilterablePsmAnnotations.getFilterablePsmAnnotation().add( xmlFilterablePsmAnnotation );
					
					xmlFilterablePsmAnnotation.setAnnotationName( PSMAnnotationTypes.IPROPHET_ANNOTATION_TYPE_ERROR );
					xmlFilterablePsmAnnotation.setSearchProgram( IProphetConstants.SEARCH_PROGRAM_NAME_IPROPHET );
					xmlFilterablePsmAnnotation.setValue( errorAnalysis.getError( result.getInterProphetScore() ) );
				}

				// handle iprophet score
				{
					FilterablePsmAnnotation xmlFilterablePsmAnnotation = new FilterablePsmAnnotation();
					xmlFilterablePsmAnnotations.getFilterablePsmAnnotation().add( xmlFilterablePsmAnnotation );
					
					xmlFilterablePsmAnnotation.setAnnotationName( PSMAnnotationTypes.IPROPHET_ANNOTATION_TYPE_SCORE );
					xmlFilterablePsmAnnotation.setSearchProgram( IProphetConstants.SEARCH_PROGRAM_NAME_IPROPHET );
					xmlFilterablePsmAnnotation.setValue( result.getInterProphetScore() );
				}
				
				// handle peptideprophet score
				{
					FilterablePsmAnnotation xmlFilterablePsmAnnotation = new FilterablePsmAnnotation();
					xmlFilterablePsmAnnotations.getFilterablePsmAnnotation().add( xmlFilterablePsmAnnotation );
					
					xmlFilterablePsmAnnotation.setAnnotationName( PSMAnnotationTypes.PPROPHET_ANNOTATION_TYPE_SCORE );
					xmlFilterablePsmAnnotation.setSearchProgram( IProphetConstants.SEARCH_PROGRAM_NAME_PPROPHET );
					xmlFilterablePsmAnnotation.setValue( result.getPeptideProphetScore() );
				}
				
				// handle kojak score
				{
					FilterablePsmAnnotation xmlFilterablePsmAnnotation = new FilterablePsmAnnotation();
					xmlFilterablePsmAnnotations.getFilterablePsmAnnotation().add( xmlFilterablePsmAnnotation );
					
					xmlFilterablePsmAnnotation.setAnnotationName( PSMAnnotationTypes.KOJAK_ANNOTATION_TYPE_SCORE );
					xmlFilterablePsmAnnotation.setSearchProgram( IProphetConstants.SEARCH_PROGRAM_NAME_KOJAK );
					xmlFilterablePsmAnnotation.setValue( result.getKojakScore() );
				}
				
				// add in the non-filterable descriptive annotations (e.g., calculated mass)
				DescriptivePsmAnnotations xmlDescriptivePsmAnnotations = new DescriptivePsmAnnotations();
				xmlPsm.setDescriptivePsmAnnotations( xmlDescriptivePsmAnnotations );
				
				{
					// handle error ppm
					DescriptivePsmAnnotation xmlDescriptivePsmAnnotation = new DescriptivePsmAnnotation();
					xmlDescriptivePsmAnnotations.getDescriptivePsmAnnotation().add( xmlDescriptivePsmAnnotation );
					
					xmlDescriptivePsmAnnotation.setAnnotationName( PSMAnnotationTypes.KOJAK_ANNOTATION_TYPE_PPMERROR );
					xmlDescriptivePsmAnnotation.setSearchProgram( IProphetConstants.SEARCH_PROGRAM_NAME_KOJAK );
					
					// try to limit this value to the chosen number of decimal places
					xmlDescriptivePsmAnnotation.setValue( String.valueOf( result.getPpmError() ) );
				}
				
				{
					// handle delta mass
					DescriptivePsmAnnotation xmlDescriptivePsmAnnotation = new DescriptivePsmAnnotation();
					xmlDescriptivePsmAnnotations.getDescriptivePsmAnnotation().add( xmlDescriptivePsmAnnotation );
					
					xmlDescriptivePsmAnnotation.setAnnotationName( PSMAnnotationTypes.KOJAK_ANNOTATION_TYPE_DELTASCORE );
					xmlDescriptivePsmAnnotation.setSearchProgram( IProphetConstants.SEARCH_PROGRAM_NAME_KOJAK );
					
					// try to limit this value to the chosen number of decimal places
					xmlDescriptivePsmAnnotation.setValue( String.valueOf( result.getDeltaScore() ) );
				}
				

				
				
			}//end iterating over all PSMs for a reported peptide
			
			
		}// end iterating over distinct reported peptides



		// add in the matched proteins section
		MatchedProteinsBuilder.getInstance().buildMatchedProteins(
				proxlInputRoot,
				fastaFile,
				peptides,
				analysis.getDecoyIdentifiers()
		);

		// add in the config file(s)
		ConfigurationFiles xmlConfigurationFiles = new ConfigurationFiles();
		proxlInputRoot.setConfigurationFiles( xmlConfigurationFiles );
		
		for( String path : analysis.getKojakConfFilePaths() ) {
			ConfigurationFile xmlConfigurationFile = new ConfigurationFile();
			xmlConfigurationFiles.getConfigurationFile().add( xmlConfigurationFile );
			
			xmlConfigurationFile.setSearchProgram( IProphetConstants.SEARCH_PROGRAM_NAME_KOJAK );
			xmlConfigurationFile.setFileName( (new File( path )).getName() );
			xmlConfigurationFile.setFileContent( Files.readAllBytes( FileSystems.getDefault().getPath( path ) ) );
		}
		
		//make the xml file
		CreateImportFileFromJavaObjectsMain.getInstance().createImportFileFromJavaObjectsMain(outfile, proxlInputRoot);
		
	}
	
}
