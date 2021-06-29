/*
 * GNU GPL v3 License
 *
 * Copyright 2016 Marialaura Bancheri
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
package TestBroker;
//import static org.junit.Assert.assertArrayEquals;
import org.junit.Test;
import BrokerSolver.*;
import monodimensionalProblemTimeDependent.ReadNetCDFLisimetro;
/**
 * Test the Broker module.
 * @author Concetta D'Amato
 */

public class TestBroker2 {

	@Test
	public void Test() throws Exception {

		String pathGrid =  "resources\\Input\\OutTest_VG.nc";
		ReadNetCDFLisimetro readNetCDF = new ReadNetCDFLisimetro();		
		double[] z= {0.2,0.4,0.6,0.8,1,1.2,1.4,1.6,1.8,2};
		double[] theta = {0.21, 0.21, 0.28, 0.32, 0.3, 0.40, 0.40, 0.40, 0.40, 0.40};// theta vector values from the bottom to the top
		double[] deltaZ = {0.2,0.2,0.2,0.2,0.2,0.2,0.2,0.2,0.2,0.2};
		double evaporation = 10;
		double transpiration = 20;
		
		
		StressFactorBroker StressFactorBrokerSolver = new StressFactorBroker();
		
		ETsBroker ETsBrokerSolver = new ETsBroker();  
		
		readNetCDF.richardsGridFilename = pathGrid;
		
		readNetCDF.read();

		StressFactorBrokerSolver.thetaWp = readNetCDF.thetaWp;
		StressFactorBrokerSolver.thetaFc = readNetCDF.thetaFc;
		StressFactorBrokerSolver.z  = z;
		StressFactorBrokerSolver.etaR = -1.5;
		StressFactorBrokerSolver.etaE = -1.8;
		StressFactorBrokerSolver.theta = theta;
		StressFactorBrokerSolver.deltaZ = deltaZ;
		StressFactorBrokerSolver.stressFactorModel = "LinearStressFactor";
		StressFactorBrokerSolver.representativeStressFactorModel = "AverageRepresentativeSF";
		//SizeWightedRepresentativeSF, AverageRepresentativeSF
		
		ETsBrokerSolver.z = z;
		ETsBrokerSolver.etaR = -1.5;
		ETsBrokerSolver.etaE = -1.8;
		ETsBrokerSolver.deltaZ = deltaZ;
		ETsBrokerSolver.transpiration = transpiration;
		ETsBrokerSolver.evaporation = evaporation;
		ETsBrokerSolver.representativeStressFactorModel = "AverageRepresentativeSF";
		//SizeWightedRepresentativeSF, AverageRepresentativeSF
		
		StressFactorBrokerSolver.solve();
		
		ETsBrokerSolver.g = StressFactorBrokerSolver.g;
		ETsBrokerSolver.GnT = StressFactorBrokerSolver.GnT;
		ETsBrokerSolver.GnE = StressFactorBrokerSolver.GnE;
		
		ETsBrokerSolver.solve();
	}
}

