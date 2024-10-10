package model.exception;

public class RepositoryException extends Exception{

    public RepositoryException(){
        super();
    }
    public RepositoryException(Exception exception){
        super(exception);
    }

    public RepositoryException(String str){
        super(str);
    }
}
