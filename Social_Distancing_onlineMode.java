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

import java.awt.event.*;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import java.awt.SystemColor;
import javax.swing.SwingConstants;
import java.awt.Color;

public class Social_Distancing {
//GUI data setting
	private JFrame frmSocialDistancing;
	private final JPanel panel = new JPanel();
	private JTextField Domestic_Input;
	private JTextField Imported_Input;
	private JTextField DailyChange_Input;
	private JTextField Progress_Input;
	private JTextField Released_Input;

//input data setting
	int dom;
	int im;
	int daily;
	int pro;
	int rel;

//result data setting
	double oneR_result;
	double naive_result;
	double tree_result;

//accuracy data setting
	double oneR_accuracy;
	double naive_accuracy;
	double tree_accuracy;

//algorithmSet data setting
	String AlgorithmSet = new String();

//main
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Social_Distancing window = new Social_Distancing();
					window.frmSocialDistancing.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Social_Distancing() throws Exception {
		initialize();
	}

	private void initialize() throws Exception {
		//GUI initial setting
		frmSocialDistancing = new JFrame();
		frmSocialDistancing.setTitle("Social Distancing");
		frmSocialDistancing.setBounds(100, 100, 496, 730);
		frmSocialDistancing.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSocialDistancing.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		//title
		JLabel Social_Distancing = new JLabel("Social Distancing");
		Social_Distancing.setBounds(63, 23, 360, 53);
		Social_Distancing.setFont(new Font("Eras Demi ITC", Font.BOLD, 40));
		panel.add(Social_Distancing);

		//input title
		JLabel TestCase = new JLabel("----------Test Case----------");
		TestCase.setHorizontalAlignment(SwingConstants.CENTER);
		TestCase.setForeground(SystemColor.activeCaptionBorder);
		TestCase.setFont(new Font("Eras Bold ITC", Font.BOLD, 28));
		TestCase.setBounds(40, 87, 395, 44);
		panel.add(TestCase);
		
		JLabel DomesticCases = new JLabel("Domestic Cases(0 ~ 434): ");
		DomesticCases.setBounds(40, 131, 309, 34);
		DomesticCases.setFont(new Font("Eras Medium ITC", Font.PLAIN, 19));
		panel.add(DomesticCases);

		JLabel ImportedCases = new JLabel("Imported Cases(1 ~ 86): ");
		ImportedCases.setBounds(40, 183, 309, 34);
		ImportedCases.setFont(new Font("Eras Medium ITC", Font.PLAIN, 19));
		panel.add(ImportedCases);

		JLabel DailyChange = new JLabel("Daily Change(-131 ~ 121): ");
		DailyChange.setBounds(40, 235, 309, 34);
		DailyChange.setFont(new Font("Eras Medium ITC", Font.PLAIN, 19));
		panel.add(DailyChange);

		JLabel InProgress = new JLabel("In Progress(8009 ~ 58021): ");
		InProgress.setBounds(40, 287, 309, 34);
		InProgress.setFont(new Font("Eras Medium ITC", Font.PLAIN, 19));
		panel.add(InProgress);

		JLabel Releasedfromquarantine = new JLabel("Released from Quarantine(7 ~ 432): ");
		Releasedfromquarantine.setBounds(40, 339, 309, 34);
		Releasedfromquarantine.setFont(new Font("Eras Medium ITC", Font.PLAIN, 19));
		panel.add(Releasedfromquarantine);

		//input data
		Domestic_Input = new JTextField();
		Domestic_Input.setHorizontalAlignment(SwingConstants.CENTER);
		Domestic_Input.setBounds(343, 131, 105, 34);
		Domestic_Input.setFont(new Font("Eras Medium ITC", Font.PLAIN, 19));
		panel.add(Domestic_Input);
		Domestic_Input.setColumns(10);

		Imported_Input = new JTextField();
		Imported_Input.setHorizontalAlignment(SwingConstants.CENTER);
		Imported_Input.setBounds(343, 183, 105, 34);
		Imported_Input.setFont(new Font("Eras Medium ITC", Font.PLAIN, 19));
		Imported_Input.setColumns(10);
		panel.add(Imported_Input);

		DailyChange_Input = new JTextField();
		DailyChange_Input.setHorizontalAlignment(SwingConstants.CENTER);
		DailyChange_Input.setBounds(343, 235, 105, 34);
		DailyChange_Input.setFont(new Font("Eras Medium ITC", Font.PLAIN, 19));
		DailyChange_Input.setColumns(10);
		panel.add(DailyChange_Input);

		Progress_Input = new JTextField();
		Progress_Input.setHorizontalAlignment(SwingConstants.CENTER);
		Progress_Input.setBounds(343, 287, 105, 34);
		Progress_Input.setFont(new Font("Eras Medium ITC", Font.PLAIN, 19));
		Progress_Input.setColumns(10);
		panel.add(Progress_Input);

		Released_Input = new JTextField();
		Released_Input.setHorizontalAlignment(SwingConstants.CENTER);
		Released_Input.setBounds(343, 339, 105, 34);
		Released_Input.setFont(new Font("Eras Medium ITC", Font.PLAIN, 19));
		Released_Input.setColumns(10);
		panel.add(Released_Input);

		//algorithm check
		JLabel Algorithm = new JLabel("----------Algorithm----------");
		Algorithm.setHorizontalAlignment(SwingConstants.CENTER);
		Algorithm.setForeground(SystemColor.activeCaptionBorder);
		Algorithm.setFont(new Font("Eras Bold ITC", Font.BOLD, 28));
		Algorithm.setBounds(40, 384, 395, 44);
		panel.add(Algorithm);
		
		JRadioButton OneRule = new JRadioButton("OneRule");
		OneRule.setHorizontalAlignment(SwingConstants.CENTER);
		OneRule.setFont(new Font("Candara Light", Font.BOLD | Font.ITALIC, 20));
		OneRule.setBounds(50, 434, 100, 27);
		OneRule.setSelected(true);
		panel.add(OneRule);

		JRadioButton NaiveBayes = new JRadioButton("NaiveBayes");
		NaiveBayes.setHorizontalAlignment(SwingConstants.CENTER);
		NaiveBayes.setFont(new Font("Candara Light", Font.BOLD | Font.ITALIC, 20));
		NaiveBayes.setBounds(144, 434, 159, 27);
		panel.add(NaiveBayes);

		JRadioButton DecisionTree = new JRadioButton("DecisionTree");
		DecisionTree.setHorizontalAlignment(SwingConstants.CENTER);
		DecisionTree.setFont(new Font("Candara Light", Font.BOLD | Font.ITALIC, 20));
		DecisionTree.setBounds(280, 434, 174, 27);
		panel.add(DecisionTree);

		ButtonGroup group = new ButtonGroup();
		group.add(OneRule);
		group.add(NaiveBayes);
		group.add(DecisionTree);

		OneRule.setActionCommand("o");
		NaiveBayes.setActionCommand("n");
		DecisionTree.setActionCommand("d");

		OneRule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AlgorithmSet = group.getSelection().getActionCommand();
			}
		});
		NaiveBayes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AlgorithmSet = group.getSelection().getActionCommand();
			}
		});
		DecisionTree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AlgorithmSet = group.getSelection().getActionCommand();
			}
		});

		//result title
		JButton testStart = new JButton("Test Strat");
		testStart.setBounds(140, 484, 199, 59);
		testStart.setFont(new Font("Eras Demi ITC", Font.PLAIN, 34));
		panel.add(testStart);
		
		JLabel Class = new JLabel(" ");
		Class.setFont(new Font("Eras Medium ITC", Font.PLAIN, 29));
		Class.setBounds(12, 562, 456, 47);
		panel.add(Class);

		JLabel Accuracy = new JLabel(" ");
		Accuracy.setFont(new Font("Eras Medium ITC", Font.PLAIN, 29));
		Accuracy.setBounds(12, 613, 456, 47);
		panel.add(Accuracy);
		
		JLabel Mistake = new JLabel(" ");
		Mistake.setHorizontalAlignment(SwingConstants.CENTER);
		Mistake.setForeground(Color.RED);
		Mistake.setFont(new Font("Eras Medium ITC", Font.PLAIN, 35));
		Mistake.setBounds(50, 585, 378, 47);
		panel.add(Mistake);

		//result
		testStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				double C;
				int mistakeCheck = 0;
				dom = Integer.parseInt(Domestic_Input.getText());
				im = Integer.parseInt(Imported_Input.getText());
				daily = Integer.parseInt(DailyChange_Input.getText());
				pro = Integer.parseInt(Progress_Input.getText());
				rel = Integer.parseInt(Released_Input.getText());
				
				//if test data input mistake, exit
				if ((dom < 0 || dom > 434) ||
						(im < 1 || im > 86) ||
						(daily < -131 || daily > 121) ||
						(pro < 8009 || pro > 58021) ||
						(rel < 7 || rel > 432)) {
					Mistake.setText("Wrong Input Data"); mistakeCheck = 1;
					//input reset
					Domestic_Input.setText("");
					Imported_Input.setText("");
					DailyChange_Input.setText("");
					Progress_Input.setText("");
					Released_Input.setText("");
					Class.setText(" "); Accuracy.setText(" ");
					}
				else Mistake.setText(" ");
				
				
				if (mistakeCheck == 0) {
				//Weka Setting-----------------------------------
				//Load Data
				try{
					DataSource source = new DataSource("C:\\Social_Distancing\\arff\\Social_Distancing.arff"); // arff는 C드라이브 속에 바로 넣는다.
				Instances trainSet = source.getDataSet();
				trainSet.setClassIndex(trainSet.numAttributes() - 1);

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

				//Cross-Validation
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

				
				//offline mode test start-----------------------------------------------------------------------------------------------
				//System.out.println(oneR.toString());
				//System.out.println(oneR_eval.toSummaryString());
				
				//System.out.println(naive.toString());
				//System.out.println(naive_eval.toSummaryString());
				
				//System.out.println(tree.toString());
				//System.out.println(tree_eval.toSummaryString());
				//offline mode test end-----------------------------------------------------------------------------------------------
				
				
				//Test
				Instances testSet = new Instances("testSet", instanceAttributes, 0);
				testSet.setClassIndex(testSet.numAttributes() - 1);
				double[] testData = new double[] { dom, im, daily, pro, rel };
				Instance testInstance = new DenseInstance(1.0, testData);
				testSet.add(testInstance);

				//Result
				oneR_result = oneR.classifyInstance(testSet.instance(testSet.size() - 1));
				naive_result = naive.classifyInstance(testSet.instance(testSet.size() - 1));
				tree_result = tree.classifyInstance(testSet.instance(testSet.size() - 1));

				//Accuracy
				oneR_accuracy = oneR_eval.pctCorrect();
				naive_accuracy = naive_eval.pctCorrect();
				tree_accuracy = tree_eval.pctCorrect();
				} catch(Exception e) {};
				
				if (AlgorithmSet == "o") {
					if (oneR_result == 0.0) C = 1;
					else if (oneR_result == 1.0) C = 2;
					else C = 2.5;
					Accuracy.setText("Accuracy: " + oneR_accuracy + "%");
					Class.setText("Social Distancing: " + C);
				} else if (AlgorithmSet == "n") {
					if (naive_result == 0.0) C = 1;
					else if (naive_result == 1.0) C = 2;
					else C = 2.5;
					Accuracy.setText("Accuracy: " + naive_accuracy + "%");
					Class.setText("Social Distancing: " + C);
				} else {
					if (tree_result == 0.0) C = 1;
					else if (tree_result == 1.0) C = 2;
					else C = 2.5;
					Accuracy.setText("Accuracy: " + tree_accuracy + "%");
					Class.setText("Social Distancing: " + C);
				}

				//input reset
				Domestic_Input.setText("");
				Imported_Input.setText("");
				DailyChange_Input.setText("");
				Progress_Input.setText("");
				Released_Input.setText("");			
				}
			}
		});
	}
}
