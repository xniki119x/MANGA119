package f1cont.niki119.manga119.helpers;

public interface LogHelper {

    default void info(Object o){
        System.out.println(o);
    }
}
