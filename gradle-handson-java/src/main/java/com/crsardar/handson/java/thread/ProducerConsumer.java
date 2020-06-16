package com.crsardar.handson.java.thread;

import java.util.concurrent.SynchronousQueue;

public class ProducerConsumer
{
    private static SynchronousQueue<Integer> synchronousQueue = new SynchronousQueue<>();

    public static void main(String[] args)
    {
       new Producer().start();
       new Consumer().start();
    }

    static class Producer extends Thread
    {
        @Override
        public void run()
        {
            try
            {
                synchronousQueue.put(0);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            while (true)
            {
                try
                {
                    int num = synchronousQueue.take();

                    System.out.println("Producer : " + num);

                    sleep(100);

                    synchronousQueue.put(num + 1);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Consumer extends Thread
    {
        @Override
        public void run()
        {
            while (true)
            {
                try
                {
                    sleep(100);

                    int num = synchronousQueue.take();

                    System.out.println("Consumer : " + num);

                    synchronousQueue.put(num + 1);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

}
