import com.crsardar.handson.java.general.reflexion.SubClassOne;
import com.crsardar.handson.java.general.reflexion.SuperClass;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class InstanceofTest
{

    @Test
    public void test()
    {

        SubClassOne subClassOne = new SubClassOne();

        System.out.println((subClassOne instanceof SuperClass));

        Assert.assertTrue((subClassOne instanceof SuperClass));
    }

    @Test
    public void checkFileNotFoundException()
    {

        File file = new File("C:/wrong/location/file.txt");
        System.out.println("Is file present = " + file.exists());
    }

    @Test
    public void nullTest()
    {
        final String FINAL_STR = "Chittaranjan";

        Assert.assertFalse(FINAL_STR == null);
    }

    @Test
    public void testExecutorSelfRescheduling()
    {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(
                3);

        Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {

                System.out.println("I am going to sleep");
                try
                {
                    Thread.currentThread().sleep(100);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }

                System.out.println("Scheduling myself");

                scheduledThreadPoolExecutor.schedule(this, 10, TimeUnit.MILLISECONDS);
            }
        });

        scheduledThreadPoolExecutor.schedule(thread, 10, TimeUnit.MILLISECONDS);

        try
        {
            Thread.currentThread().sleep(10000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

    }

    @Test
    public void testExecutorCancellOnGoing()
    {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(
                3);

        Semaphore semaphoreLock = new Semaphore(1);

        Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    semaphoreLock.acquire();
                    System.out.println("Lock acquired.....");
                    System.out.println("semaphoreLock.availablePermits() = " + semaphoreLock.availablePermits());
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }

                int count = 0;
                while (count<10)
                {
                    System.out.println("I am Back!!! count = " + count);
                    count++;

                    try
                    {
                        Thread.currentThread().sleep(10);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                        break;
                    }
                }
            }
        });

        ScheduledFuture<?> scheduledFuture = scheduledThreadPoolExecutor
                .schedule(thread, 10, TimeUnit.MILLISECONDS);

        try
        {
            Thread.currentThread().sleep(1000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        System.out.println("Before Unlock call semaphoreLock.availablePermits() = " + semaphoreLock.availablePermits());

        semaphoreLock.release();

        System.out.println("semaphoreLock.availablePermits() = " + semaphoreLock.availablePermits());

//        boolean cancel = scheduledFuture.cancel(true);
//        System.out.println("Ongoing task canceled? = " + cancel);
//
//        cancel = scheduledFuture.cancel(true);
//        System.out.println("Ongoing task canceled? = " + cancel);

        while(true);

    }
}
