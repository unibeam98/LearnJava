package CJV.v1ch14.threadPool;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

public class ThreadPoolTest {
    public static void main(String[] args)
    {
        try (Scanner in = new Scanner(System.in)){
            System.out.println("Enter base director(e.g. /opt/jdk7.0/src):");
            String directory = in.nextLine();
            System.out.println("Enter keyword(e.g. volatile):");
            String keyword = in.nextLine();

            ExecutorService pool = Executors.newCachedThreadPool();

            MatchCounter counter = new MatchCounter(new File(directory), keyword, pool);
            Future<Integer> result = pool.submit(counter);

            try {
                System.out.println(result.get() + " matching files.");
            }
            catch (ExecutionException | InterruptedException e){
                e.printStackTrace();
            }

            pool.shutdown();

            int largePoolSize = ((ThreadPoolExecutor) pool).getLargestPoolSize();
            System.out.println("largest pool size=" + largePoolSize);
        }
    }
}

class MatchCounter implements Callable<Integer> {
    private File directory;
    private String keyword;
    private ExecutorService pool;
    private int count;

    public MatchCounter(File directory, String keyword, ExecutorService pool){
        this.directory = directory;
        this.keyword = keyword;
        this.pool = pool;
    }

    public Integer call(){
        int count = 0;
        try {
            File[] files = directory.listFiles();
            List<Future<Integer>> results = new ArrayList<>();

            for (File file : files) {
                if (file.isDirectory()) {
                    MatchCounter counter = new MatchCounter(file, keyword, pool);
                    Future<Integer> result = pool.submit(counter);
                    results.add(result);
                } else {
                    if (search(file))
                        count++;
                }
            }

            for(Future<Integer> result : results){
                try {
                    count += result.get();
                }catch (ExecutionException e){
                    e.printStackTrace();
                }
            }
        }catch (InterruptedException e){}
        return count;
    }

    public boolean search(File file) {
        try {
            try (Scanner in = new Scanner(file, "UTF-8")){
                boolean found = false;
                while (!found && in.hasNextLine()){
                    String line = in.nextLine();
                    if(line.contains(keyword))
                        found = true;
                }
                return found;
            }
        }catch (IOException e){
            return false;
        }
    }

}
