import weka.classifiers.bayes.*;
import weka.classifiers.*;
import weka.classifiers.trees.*;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.classifiers.rules.*;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



public class Data{
	public static void main(String[] args) throws Exception {
		//Social_Distancing data--------------------------------------------------------------------------------------------
		System.out.println("<<<<<Social_Distancing>>>>>\n");
		
		
		//Load Data
		DataSource source = new DataSource("C:\\Social_Distancing.arff"); // arff는 C드라이브 속에 바로 넣는다.
		Instances trainSet = source.getDataSet();
		trainSet.setClassIndex(trainSet.numAttributes() - 1);
		
		Scanner scan = new Scanner(System.in);
		
		
		//Model Instance
		NaiveBayes naive = new NaiveBayes();
		OneR oneR = new OneR();
		J48 tree = new J48();
		
		
		//Training Set
		oneR.buildClassifier(trainSet);
		naive.buildClassifier(trainSet);
		tree.buildClassifier(trainSet);

		
		//Evaluation
		Evaluation oneR_eval = new Evaluation(trainSet);
		Evaluation naive_eval = new Evaluation(trainSet);
		Evaluation tree_eval = new Evaluation(trainSet);


		// Cross-Validation
		oneR_eval.crossValidateModel(oneR, trainSet, 10, new Random(1));
		naive_eval.crossValidateModel(naive, trainSet, 10, new Random(1));
		tree_eval.crossValidateModel(tree, trainSet, 10, new Random(1));

	
		//Set Attributes
		Attribute attr1 = new Attribute("Domestic_Cases");
		Attribute attr2 = new Attribute("Imported_Cases");
		Attribute attr3 = new Attribute("Daily_Change");
		Attribute attr4 = new Attribute("In_Progress");
		Attribute attr5 = new Attribute("Released_from_Quarantine");
				
		List<String> cls = new ArrayList<String>(3);
		cls.add("1");
		cls.add("2");
		cls.add("2.5");
		Attribute attr6 = new Attribute("Social_Distancing", cls);
				
		ArrayList<Attribute> instanceAttributes = new ArrayList<Attribute>(7);
		instanceAttributes.add(attr1);
		instanceAttributes.add(attr2);
		instanceAttributes.add(attr3);
		instanceAttributes.add(attr4);
		instanceAttributes.add(attr5);
		instanceAttributes.add(attr6);
		
		
		//Input data (새로운 시험 데이터의 입력 부분)
		while(true) {
			String str;
			int atr1, atr2, atr3, atr4, atr5, atr6;
			
			System.out.print("Domestic_Cases\nEnter range value(0 ~ 434): ");
			atr1 = scan.nextInt();
			if(0 > atr1 || 434 < atr1) {
				System.out.println("Wrong Input");
				continue;
			}
			
			System.out.print("Imported_Cases\nEnter range value(1 ~ 86): ");
			atr2 = scan.nextInt();
			if(1 > atr2 || 86 < atr2) {
				System.out.println("Wrong Input");
				continue;
			}
			
			System.out.print("Daily_Change\nEnter range value(-131 ~ 121): ");
			atr3 = scan.nextInt();
			if(-131 > atr3 || 121 < atr3) {
				System.out.println("Wrong Input");
				continue;
			}
			
			System.out.print("In_Progress\nEnter range value(8009 ~ 58021): ");
			atr4 = scan.nextInt();
			if(8009 > atr4 || 58021 < atr4) {
				System.out.println("Wrong Input");
				continue;
			}
			
			System.out.print("Released_from_Quarantine\nEnter range value(7 ~ 432): ");
			atr5 = scan.nextInt();
			if(7 > atr5 || 432 < atr5) {
				System.out.println("Wrong Input");
				continue;
			}

	
		//Test (결과로 얻은 분류 모델의 지식베이스 구축)
		Instances testSet = new Instances("testSet", instanceAttributes, 0);
		testSet.setClassIndex(testSet.numAttributes() - 1);
			
		double[] testData = new double[] {
				atr1, atr2, atr3, atr4, atr5
		};
		Instance testInstance = new DenseInstance(1.0, testData);
		testSet.add(testInstance);
		
		
		//Result & Accuracy (시험 데이터의 분류결과 출력 부분) - OneR
		System.out.println("\n<<<<<Result>>>>>\nOneR");
		double oneR_result = oneR.classifyInstance(testSet.instance(testSet.size() - 1));
		if(oneR_result == 0.0)
			System.out.println("Class(Social_Distancing) : 1");
		else if(oneR_result == 1.0)
			System.out.println("Class(Social_Distancing) : 2");
		else
			System.out.println("Class(Social_Distancing) : 2.5");
		System.out.println("Accuray : " + oneR_eval.pctCorrect()+"%\n");
			
		
		//Result & Accuracy (시험 데이터의 분류결과 출력 부분) - NaiveBayes
		System.out.println("NaiveBayes");
		double naive_result = naive.classifyInstance(testSet.instance(testSet.size() - 1));
		if(naive_result == 0.0)
			System.out.println("Class(Social_Distancing) : 1");
		else if(naive_result == 1.0)
			System.out.println("Class(Social_Distancing) : 2");
		else
			System.out.println("Class(Social_Distancing) : 2.5");
		System.out.println("Accuray : " + naive_eval.pctCorrect()+"%\n");
		
		
		//Result & Accuracy (시험 데이터의 분류결과 출력 부분) - J48 (Decision Tree)
		System.out.println("J48(Decision Tree)");
		double tree_result = tree.classifyInstance(testSet.instance(testSet.size() - 1));
		if(tree_result == 0.0)
			System.out.println("Class(Social_Distancing) : 1");
		else if(tree_result == 1.0)
			System.out.println("Class(Social_Distancing) : 2");
		else
			System.out.println("Class(Social_Distancing) : 2.5");
		System.out.println("Accuray : " + tree_eval.pctCorrect()+"%\n");
		break; }
		
		
		//.exe exit setting
		int exit;
		System.out.println("1을 입력하시면 종료됩니다.");
		while(true) {
			exit = scan.nextInt();
			if(exit==1) break;
		}
	}
}	