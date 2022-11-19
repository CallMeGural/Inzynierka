package pl.gf.umlcd.connections;

import javafx.scene.control.Label;
import pl.gf.umlcd.ClassEntity;
import pl.gf.umlcd.exceptions.*;

public class WrongConnection {

    public void associationBetweenOtherTypes(ClassEntity start, ClassEntity end) throws WrongAssociationException {
        Label label = new Label();
        if(start.getClass().getSimpleName().equals("ClassEntity") &&
                    !start.getClass().equals(end.getClass()))
            label = (Label) end.getVbox().getChildren().get(0);
            if(!label.getText().equals("<<enum>>"))
                //class and nonEnum
                throw new WrongAssociationException();
            if(start.getClass().getSimpleName().equals("OtherClassEntity") &&
                    !start.getClass().equals(end.getClass()))
                label = (Label) start.getVbox().getChildren().get(0);
                if(!label.getText().equals("<<enum>>"))
                //nonEnum and class
                throw new WrongAssociationException();
            if(start.getClass().getSimpleName().equals("OtherClassEntity") &&
            end.getClass().getSimpleName().equals("OtherClassEntity")) {
                //nonClass and nonClass
                throw new WrongAssociationException();
            }
    }

    public void associationBetweenEnumAndOther(ClassEntity start, ClassEntity end) throws WrongEnumConnectionException {
        if(start.getClass().getSimpleName().equals("OtherClassEntity")) {
            Label label = (Label) start.getVbox().getChildren().get(0);
            if(label.getText().equals("<<enum>>") && end.getClass().getSimpleName().equals("OtherClassEntity"))
                //enum and nonClass
                throw new WrongEnumConnectionException();
        }
        if(end.getClass().getSimpleName().equals("OtherClassEntity")) {
            Label label = (Label) end.getVbox().getChildren().get(0);
            if(label.getText().equals("<<enum>>") && start.getClass().getSimpleName().equals("OtherClassEntity"))
                //nonClass and enum
                throw new WrongEnumConnectionException();
        }
    }

    public void dependencyBetweenNonClasses(ClassEntity start, ClassEntity end) throws WrongDependencyException {
        if(start.getClass().getSimpleName().equals("OtherClassEntity") ||
        end.getClass().getSimpleName().equals("OtherClassEntity")) throw new WrongDependencyException();
    }

    public void nonAssociationBetweenEnum(ClassEntity start, ClassEntity end) throws WrongEnumConnectionException {
        if(start.getClass().getSimpleName().equals("OtherClassEntity")) {
            Label label = (Label) start.getVbox().getChildren().get(0);
            if(label.getText().equals("<<enum>>")) throw new WrongEnumConnectionException();
        }
        if(end.getClass().getSimpleName().equals("OtherClassEntity")) {
            Label label = (Label) end.getVbox().getChildren().get(0);
            if(label.getText().equals("<<enum>>")) throw new WrongEnumConnectionException();
        }
    }

    public void inheritanceBetweenOtherTypes(ClassEntity start, ClassEntity end) throws WrongInheritanceException {
        if(start.getClass().getSimpleName().equals("ClassEntity") &&
        end.getClass().getSimpleName().equals("OtherClassEntity")) throw new WrongInheritanceException();

        if(start.getClass().getSimpleName().equals("OtherClassEntity") &&
                end.getClass().getSimpleName().equals("ClassEntity")) throw new WrongInheritanceException();

        if(start.getClass().getSimpleName().equals("OtherClassEntity") &&
                end.getClass().getSimpleName().equals("OtherClassEntity")) {
            Label labelStart = (Label) end.getVbox().getChildren().get(0);
            Label labelEnd = (Label) end.getVbox().getChildren().get(1);
            if(!labelStart.getText().equals("<<interface>>")
            && !labelEnd.getText().equals("<<interface>>")) throw new WrongInheritanceException();
        }

    }

    public void wrongRealization(ClassEntity start, ClassEntity end) throws WrongRealizationException {
        if(start.getClass().getSimpleName().equals("ClassEntity")) throw new WrongRealizationException();
        if(start.getClass().getSimpleName().equals("OtherClassEntity")) {
            Label label = (Label) start.getVbox().getChildren().get(0);
            System.out.println(label.getText());
            if (!label.getText().equals("<<interface>>"))
                throw new WrongRealizationException();
        }
        if(!(end.getClass().getSimpleName().equals("ClassEntity"))) throw new WrongRealizationException();
    }
    //AGGREGATION AND COMPOSITION
}
