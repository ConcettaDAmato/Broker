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
package GeneralStressFactor_StressedETs;
/**
 * The stress factor and stressedETs abstract class.
 * @author Concetta D'Amato
 */

public abstract class GeneralSF_ETs {
	
	public double[] z;   			// z coordinate read from grid NetCDF file
	public double zR;    			// Depth of the root from the bottom
	public double zE;    			// Depth of the evaporation layer from the bottom
	public double etaR;    			// Depth of the root 
	public double etaE;    			// Depth of the Evaporation layer
	public double[] deltaZ; 		// Vector containing the length of each control volume
	public int NUM_CONTROL_VOLUMES; // Number of control volume for domain discetrization
	public double totalDepth;
	public double n; 				// nR counts the number of control volumes
	public double G; 				//Representative stress factor
	public double GE; 				//Representative stress factor in the evaporation layer
	public double[] Gn;
	public double[] fluxRefs;       // vector of flux divided in the control volumes
	
	
	/**
	 * General constructor used to pass the values of variables
	 */
	
	public GeneralSF_ETs (double[] z, double[] deltaZ, int NUM_CONTROL_VOLUMES,double totalDepth) {
		this.z  = z;
		this.deltaZ = deltaZ;
		this.NUM_CONTROL_VOLUMES = NUM_CONTROL_VOLUMES;  
		this.totalDepth = totalDepth;
		n= 0;
		G = 0;
		fluxRefs = new double[NUM_CONTROL_VOLUMES-1];
		Gn = new double [2];
		}
	
	/*
	 * This method compute the representative stress factor given the values of stress factor for each control volumes
	 * @param g
	 * @return G
	 */
	
	public abstract double [] computeRepresentativeStressFactor (double[]g, double etaRef, double zRef);
	
	public abstract double [] computeStressedETs (double[]g,double[] Gn,double fluxRef, double zRef);
}