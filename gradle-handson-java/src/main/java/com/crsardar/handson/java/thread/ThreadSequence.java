package com.crsardar.handson.java.thread;

public class ThreadSequence
{
    private static final Object myLock = new Object();

    private static int number = 0;

    public static void main(String[] args)
    {
        new ThreadOne().start();
        new ThreadTwo().start();
    }

    static class ThreadOne extends Thread
    {
        @Override
        public void run()
        {
            while (true)
            {
                try
                {
                    Thread.sleep(100);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                System.out.println("Thread 1 : " + number);

                number++;

                synchronized (myLock)
                {
                    myLock.notify();

                    try
                    {
                        myLock.wait();
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }

        }
    }

    static class ThreadTwo extends Thread
    {
        @Override
        public void run()
        {
            while (true)
            {
                try
                {
                    Thread.sleep(100);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }

                System.out.println("Thread 2 : " + number);

                number++;
                synchronized (myLock)
                {
                    myLock.notify();

                    try
                    {
                        myLock.wait();
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }

            }

        }
    }
}
