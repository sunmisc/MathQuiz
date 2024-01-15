package me.astoriamine.quiz;

@FunctionalInterface
public interface Exit {

    Exit NEVER = () -> false;

    boolean ready();


}
