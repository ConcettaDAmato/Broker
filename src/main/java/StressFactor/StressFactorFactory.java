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
package StressFactor;
import StressFactor.LinearStressFactor;
/**
 * A simple design factory for creating a StressFactor objects.
 * @author Concetta D'Amato
 */
public class StressFactorFactory {

	/**
	 * Creates a new stress factor object.
	 * @param type name of the Stress Factor model
	 * @param theta_Wp dimensionless wilting point
	 * @param theta_Fc dimensionless field capacity
	 * @param z water content
	 * @param zR depth of the root
	 * @param dx vector containing the length of each control volume
	 * @param NUM_CONTROL_VOLUMES number of control volume for domain discetrization
	 * @return stressFactor g
	 */
	
	public StressFactor createStressFactor (String type, double[] thetaWp, double[] thetaFc, double[] z, double[] deltaZ, int NUM_CONTROL_VOLUMES, double totalDepth) {

		StressFactor stressFactor = null;
		if(type.equalsIgnoreCase("LinearStressFactor") || type.equalsIgnoreCase("LinearStressFactor")){
			stressFactor = new LinearStressFactor(thetaWp, thetaFc, z,deltaZ, NUM_CONTROL_VOLUMES, totalDepth);}
		return stressFactor;
	}

		
}
