/*
 * GNU GPL v3 License
 *
 * Copyright 2017 
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package BrokerSolver;
import oms3.annotations.Author;
import oms3.annotations.Description;
import oms3.annotations.Documentation;
import oms3.annotations.Execute;
import oms3.annotations.In;
import oms3.annotations.Out;
import oms3.annotations.Unit;
import java.util.Arrays;
import GeneralStressFactor_StressedETs.*;
@Description("This class is used to connect the Richard model with the evapotranspiration model, calculating the evapotranspiration for each control volumes.")
@Documentation("")
@Author(name = "Concetta D'Amato", contact = "concetta.damato@unitn.it")


public class ETsBroker {
	
	@Description("Depth of the root.")
	@In
	@Unit("m")
	public double etaR;
	
	@Description("Depth of the Evaporation layer.")
	@In 
	@Unit("m")
	public double etaE;
	
	@Description("z coordinate read from grid NetCDF file.")
	@In
	@Unit("m")
	public double[] z;
	
	@Description("Depth of the root from the bottom.")
	@In
	@Unit("m")
	public double zR;
	
	@Description("Depth of the evaporation layer from the bottom")
	@In
	@Unit("m")
	public double zE;
	
	@Description("Depth of the domain")
	@In
	@Unit("m")
	public double totalDepth;
	
	@Description("The stressed Evapotranspiration from Prospero model.")
	@In
	@Unit("mm/s")
	public double StressedET;
	
	@Description("The stressed Transpiration from Prospero model.")
	@In
	@Unit("mm/s")
	public double transpiration;
	
	@Description("The stressed Evaporation from Prospero model.")
	@In
	@Unit("mm/s")
	public double evaporation;
	
	@Description("Vector containing the length of each control volume")
	@In
	@Unit("m")
	public double[] deltaZ;
	
	@Description("Number of control volume for domain discetrization")
	@In
	@Unit ("-")
	public int NUM_CONTROL_VOLUMES;
	
	@Description("Representative Stress Factor can be evaluated in different way"
			    + " Average method --> AverageRepresentativeSF"
			    + " Weighted average method --> SizeWightedRepresentativeSF")
	@In
	public String representativeStressFactorModel;
	
	@Description("The stress factor for each control volumes")
	@In
	@Unit("-")
	public double[] g;
	
	
	@Description("Vector of G and n, for transpiration and evaporation")
	@In
	@Unit("-")
	public double[] GnT;
	
	@Description("Vector of G and n, for evaporation")
	@In
	@Unit("-")
	public double[] GnE;
	
	@Description("The stressed Evapotranspiration for each control volumes")
	@Out
	@Unit("mm/s")
	public double[] StressedETs;
	
	@Description("The stressed Transpiration for each control volumes")
	@Unit("mm/s") 
	public double[] transpirations;
	
	@Description("The stressed Evaporation for each control volumes within the Evaporation layer")
	@Unit("mm/s") 
	public double[] evaporations;
	
	@Description("It is needed to iterate on the date")
	int step;
	
	/////////////////////////////////////////////////////////////////////////////
	

	@Description("Object dealing with stress factor model representative of the domain")
	GeneralSF_ETs representativeSF;
	

	@Execute
	public void solve() {
		
		if(step==0){
			NUM_CONTROL_VOLUMES = z.length;
			totalDepth = z[NUM_CONTROL_VOLUMES -1];
			StressedETs = new double [NUM_CONTROL_VOLUMES -1];
		
			FactoryGeneralSF_ETs representativeSFFactory= new FactoryGeneralSF_ETs();
			representativeSF = representativeSFFactory.createRepresentativeStressFactor(representativeStressFactorModel, z, deltaZ, NUM_CONTROL_VOLUMES, totalDepth);
		
			zR = totalDepth + etaR;
			zE = totalDepth + etaE;
		}	
		
		transpirations = representativeSF.computeStressedETs(g,GnT,transpiration,zR);
		evaporations = representativeSF.computeStressedETs(g,GnE,evaporation,zE);
		
		for(int i = 0; i<=transpirations.length-1; i=i+1) {
	    	StressedETs [i] = evaporations[i] + transpirations[i];}
		
		//System.out.println("\n\nEvaporations = "+Arrays.toString(evaporations));
		//System.out.println("\n\nTranspirations = "+Arrays.toString(transpirations));
		//System.out.println("\n\nStressedETs = "+Arrays.toString(StressedETs));
		System.out.println("\n\nETsBroker Finished");
		//System.out.println("z = "+Arrays.toString(z));
		//System.out.println("\n\nStressedET  = "+ StressedET);
		
		step++;
	}
}
