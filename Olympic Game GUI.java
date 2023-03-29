import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.plaf.DimensionUIResource;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.lang.Math;
import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;
import java.util.*;

class Olympic
{
    private int NO;
    private String country;
    private double [] score;
    private int rank;

    //default constructor
    public Olympic(String country)
    {
        this.NO = 10;
        this.country = country;
        this.score = new double [NO];
        processScores();
    }

    //copy constructor
    public Olympic(Olympic oly)
    {
        this.country = oly.country;
    }

    //method to get 10 random scores
    public void processScores()
    {
        Random rand = new Random();

        for (int i = 0; i < score.length; i++)
        {
            score[i] = rand.nextDouble() * 101.0;
        }
    }

    //method to get the total score of 10 judges
    public double totalScores()
    {
        double total = 0.0;

        for (int i = 0; i < score.length; i++)
        {
            total += score[i];
        }

        return total;
    }

    //mutator method for rank
    public void set(int rank)
    {
        this.rank = rank;
    }

    public int getRank()
    {
        return rank;
    }

    public String getName()
    {
        return country;
    }

    private double [] getScoreArray()
    {
        return score;
    }

    public String toString()
    {
        return String.format("Rank %d : %s (%.2f)", getRank(), getName(), totalScores());
    }
}

class OlympicFrame extends JFrame implements ActionListener
{
    private JButton [] jbArray;
    private final String [] countryArray = {"USA", "SPAIN", "CHINA", "JAPAN", "ITALY", "GERMANY",
                                            "FRANCE", "BRAZIL", "NETHERLAND", "POLAND", "RUSSIA", "UKRAINE"};
    private ArrayList <Olympic> alist = new ArrayList<Olympic>();
    Double[] totalscore = new Double[countryArray.length];

    public OlympicFrame()
    {
        super ("Olympic 2021");
        setLayout(new GridLayout(3, 4));
        constructAList();

        jbArray = new JButton[countryArray.length];

        //display each picture
        for (int i = 0; i < countryArray.length; i++)
        {
            Icon ic = new ImageIcon (String.valueOf (i + 1) + ".jpg");
            jbArray [i] = new JButton (ic);
            add (jbArray [i]);
            jbArray[i].addActionListener (this);
        }

        int k = 0;

        //initialise score array
        for (Olympic oly : alist)
        {
            totalscore[k] = oly.totalScores();
            k++;
        }

        //sort score array in descending order
        Arrays.sort(totalscore, Collections.reverseOrder());

        //Set rank for each oly object
        for (int i = 0; i < countryArray.length; i++)
            {
                for (Olympic oly : alist)
                {
                    if(totalscore[i] == oly.totalScores())
                    oly.set(i + 1);
                }

            }
}

    private void constructAList()
    {
        for (String country : countryArray)
        {
            alist.add(new Olympic(country));
        }
    }

    private int getRank(Double [] totalscore, double d)
    {
        int rank = 0;

        for (int i = 0; i < totalscore.length; i++)
        {
            if (totalscore[i] == d)
            {
                rank = i+1;     
            }    
        }

        return rank;
    }

    private String getFinalRanking()
    {
        String ranks = " ";
        for (int i = 1; i < totalscore.length + 1; i++)
        {
            //display ranking string in panel
            ranks += String.format("Rank %d: %s (%.2f)%n", i, getCountry(alist, i), getScores(alist, getCountry(alist, i)));

        }
        
        return ranks;
    }

    private String getCountry(ArrayList<Olympic> alist, int n)
    {
        String countryc = "";
        for (Olympic oly : alist)
		{
			for (double d : totalscore)
			{
				if(totalscore[n-1] == oly.totalScores())
				    countryc = oly.getName();
			}
			
		}
        return countryc;
    }

    
    private double getScores(ArrayList<Olympic> alist, String name)
	{
        double scoring = 0;
		for (Olympic oly : alist)
        {
            if(oly.getName() == name)
            scoring = oly.totalScores();
        }
		return scoring;
	}

    @Override
		public void actionPerformed (ActionEvent e)
		{
            int i = 0;
            for (JButton jb : jbArray)
            {

                if(e.getSource() == jb)
                {
                    //	Icon ic = new ImageIcon (String.valueOf (i + 1) + ".jpg");
                    Icon ic = new ImageIcon("trophy.jpg");

                    //display rank for each jb button
                    jb.setText("Rank " + getRank(totalscore, getScores(alist, countryArray[i])));
			        JOptionPane.showMessageDialog (null, "FINAL RANKING\n" + getFinalRanking(), "Miss World 2021",
											JOptionPane.PLAIN_MESSAGE, ic);
                }
                    i++;
            }
		}
    }



class Olympic_Main
{
	public static void main (String [ ] args)
	{
		OlympicFrame frame = new OlympicFrame();
        frame.setSize (1000, 600);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
	}
}