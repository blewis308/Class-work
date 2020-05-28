using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace MathQuiz
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        Random randomizer = new Random();

        int addend1;
        int addend2;

        int minuend;
        int subtrahend;

        int multiplicand;
        int multiplier;

        int dividend;
        int divisor;

        int timeleft;

        public void Start_The_Quiz()
        {
            addend1 = randomizer.Next(51);
            addend2 = randomizer.Next(51);

            plusLeftLabel.Text = addend1.ToString();
            plusRightLabel.Text = addend2.ToString();

            sum.Value = 0;

            minuend = randomizer.Next(1, 101);
            subtrahend = randomizer.Next(1, minuend);
            minusLeftLabel.Text = minuend.ToString();
            minusRightLabel.Text = subtrahend.ToString();
            difference.Value = 0;

            multiplicand = randomizer.Next(2, 11);
            multiplier = randomizer.Next(2, 11);
            timesLeftLabel.Text = multiplicand.ToString();
            timesRightLabel.Text = multiplier.ToString();
            product.Value = 0;

            divisor = randomizer.Next(2, 11);
            int temporaryQuotient = randomizer.Next(2, 11);
            dividend = divisor * temporaryQuotient;
            dividedLeftLabel.Text = dividend.ToString();
            dividedRightLabel.Text = divisor.ToString();
            quotent.Value = 0;

            timeleft = 30;
            timeLabel.Text = "30 seconds";
            timer1.Start();
        }

        private void startButton_Click(object sender, EventArgs e)
        {
            Start_The_Quiz();
            startButton.Enabled = false;
        }

        private void timer1_Tick(object sender, EventArgs e)
        {
            if (CheckTheAnswer())
            {
                timer1.Stop();
                MessageBox.Show("You got all the answers right!", "Congrats!");
                startButton.Enabled = true;
            }
            else if (timeleft > 0)
            {
                timeleft -= 1;
                timeLabel.Text = timeleft + " seconds";
            }
            else
            {
                timer1.Stop();
                timeLabel.Text = "Time's up!";
                MessageBox.Show("You didn't finish in time.", "Sorry!");
                sum.Value = addend1 + addend2;
                difference.Value = minuend - subtrahend;
                product.Value = multiplicand * multiplier;
                quotent.Value = dividend / divisor;
                startButton.Enabled = true;
            }
        }

        private void pauseTheQuiz()
        {
            timer1.Stop();
            sum.Enabled = false;
            difference.Enabled = false;
            product.Enabled = false;
            quotent.Enabled = false;

            pauseButton.Text = "Unpause";
        }

        private void unpauseTheQuiz()
        {
            sum.Enabled = true;
            difference.Enabled = true;
            product.Enabled = true;
            quotent.Enabled = true;

            pauseButton.Text = "Pause";

            timer1.Start();
        }

        private bool CheckTheAnswer()
        {
            if ((addend1 + addend2 == sum.Value) 
                && (minuend - subtrahend == difference.Value)
                && (multiplicand * multiplier == product.Value)
                && (dividend / divisor == quotent.Value))
                return true;
            else
                return false;
        }

        private void answer_Enter(object sender, EventArgs e)
        {
            NumericUpDown answerBox = sender as NumericUpDown;

            if(answerBox != null)
            {
                int lengthOfAnswer = answerBox.Value.ToString().Length;
                answerBox.Select(0, lengthOfAnswer);
            }
        }

        private void pauseButton_Click(object sender, EventArgs e)
        {
            if (pauseButton.Text == "Pause")
            {
                pauseTheQuiz();
                startButton.Enabled = false;
            }
            else if (pauseButton.Text == "Unpause")
            {
                unpauseTheQuiz();
                startButton.Enabled = false;
            }
        }
    }
}
