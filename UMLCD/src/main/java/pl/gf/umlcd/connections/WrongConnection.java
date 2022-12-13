package pl.gf.umlcd.connections;

import javafx.scene.control.Label;
import pl.gf.umlcd.ClassEntity;
import pl.gf.umlcd.exceptions.*;

public class WrongConnection {

    public void associationBetweenOtherTypes(ClassEntity start, ClassEntity end) throws WrongAssociationException {
        Label label;
        if(start.getClass().getSimpleName().equals("ClassEntity") &&
                    !start.getClass().equals(end.getClass())) {
            /*label = (Label) end.getVBox().getChildren().get(0);
            if(!label.getText().equals("<<enum>>"))*/
                //class and nonEnum
                throw new WrongAssociationException();
        }
        if(end.getClass().getSimpleName().equals("ClassEntity") &&
                !start.getClass().equals(end.getClass())) {
            /*label = (Label) start.getVBox().getChildren().get(0);
            if(!label.getText().equals("<<enum>>"))*/
                //nonEnum and class
                throw new WrongAssociationException();
        }

        if(start.getClass().getSimpleName().equals("OtherClassEntity") &&
            end.getClass().getSimpleName().equals("OtherClassEntity")) {
            //nonClass and nonClass
            throw new WrongAssociationException();
        }
    }

    public void wrongDirectedAssociationFlow(ClassEntity start, ClassEntity end) throws WrongDirectedAssociationException {
        Label label;
        if(start.getClass().getSimpleName().equals("OtherClassEntity")) {
            label = (Label) start.getVBox().getChildren().get(0);
            if(label.getText().equals("<<enum>>"))
                throw new WrongDirectedAssociationException();
        }
        if(start.getClass().getSimpleName().equals("ClassEntity") && end.getClass().getSimpleName().equals("OtherClassEntity")) {
             label = (Label) end.getVBox().getChildren().get(0);
            if(!label.getText().equals("<<enum>>")) throw new WrongDirectedAssociationException();
        }
    }

    public void dependencyBetweenNonClasses(ClassEntity start, ClassEntity end) throws WrongDependencyException {
        /*if(start.getClass().getSimpleName().equals("OtherClassEntity")) {
            Label label = (Label) start.getVbox().getChildren().get(0);
            if(!label.getText().equals("enumeration")) throw new WrongDependencyException();
        }
        if(end.getClass().getSimpleName().equals("OtherClassEntity")) throw new WrongDependencyException();*/
        if(start.getClass().getSimpleName().equals("OtherClassEntity") ||
                end.getClass().getSimpleName().equals("OtherClassEntity")) throw new WrongDependencyException();
    }

    public void inheritanceBetweenOtherTypes(ClassEntity start, ClassEntity end) throws WrongInheritanceException {
        if(start.getClass().getSimpleName().equals("ClassEntity") &&
        end.getClass().getSimpleName().equals("OtherClassEntity")) throw new WrongInheritanceException();

        if(start.getClass().getSimpleName().equals("OtherClassEntity") &&
                end.getClass().getSimpleName().equals("ClassEntity")) throw new WrongInheritanceException();

        if(start.getClass().getSimpleName().equals("OtherClassEntity") &&
                end.getClass().getSimpleName().equals("OtherClassEntity")) {
            Label labelStart = (Label) end.getVBox().getChildren().get(0);
            Label labelEnd = (Label) end.getVBox().getChildren().get(1);
            if(!labelStart.getText().equals("<<interface>>")
            && !labelEnd.getText().equals("<<interface>>")) throw new WrongInheritanceException();
        }

    }

    public void wrongRealization(ClassEntity start, ClassEntity end) throws WrongRealizationException {
        if(start.getClass().getSimpleName().equals("ClassEntity")) throw new WrongRealizationException();
        if(start.getClass().getSimpleName().equals("OtherClassEntity")) {
            Label label = (Label) start.getVBox().getChildren().get(0);
            System.out.println(label.getText());
            if (!label.getText().equals("<<interface>>"))
                throw new WrongRealizationException();
        }
        if(!(end.getClass().getSimpleName().equals("ClassEntity"))) throw new WrongRealizationException();
    }

    public void theSameNodesConnected(ClassEntity start, ClassEntity end) throws TheSameNodesConnectionException {
        if(start == end) throw new TheSameNodesConnectionException();
    }

    public void wrongAggregationOrComposition(ClassEntity start, ClassEntity end) throws WrongDirectedAssociationException {
        Label label;
        if(end.getClass().getSimpleName().equals("OtherClassEntity")) {
            label = (Label) end.getVBox().getChildren().get(0);
            if(!label.getText().equals("<<interface>>")) throw new WrongDirectedAssociationException();
        }
        if(start.getClass().getSimpleName().equals("OtherClassEntity"))
            throw new WrongDirectedAssociationException();
    }
}
