package pl.gf.umlcd.connections;

import javafx.scene.control.Label;
import pl.gf.umlcd.ClassEntity;
import pl.gf.umlcd.exceptions.*;

public class WrongConnection {

    public void associationBetweenOtherTypes(ClassEntity start, ClassEntity end) throws WrongAssociationException {
        Label label;
        if(start.getClass().getSimpleName().equals("OtherClassEntity")) {
            label = (Label) start.getVBox().getChildren().get(0);
            if(label.getText().equals("<<enum>>"))
                throw new WrongAssociationException();
        }
        if(end.getClass().getSimpleName().equals("OtherClassEntity")) {
            label = (Label) end.getVBox().getChildren().get(0);
            if(label.getText().equals("<<enum>>"))
                throw new WrongAssociationException();
        }
    }

    public void wrongDirectedAssociationFlow(ClassEntity end) throws WrongDirectedAssociationException {
        if(end.getClass().getSimpleName().equals("OtherClassEntity")) {
            Label label = (Label) end.getVBox().getChildren().get(0);
            if(label.getText().equals("<<enum>>")) throw new WrongDirectedAssociationException();
        }
    }

    public void wrongDependency(ClassEntity end) throws WrongDependencyException {
        if(end.getClass().getSimpleName().equals("OtherClassEntity")) {
            Label label = (Label) end.getVBox().getChildren().get(0);
            if(label.getText().equals("<<enum>>")) throw new WrongDependencyException();
        }
    }

    public void inheritanceBetweenOtherTypes(ClassEntity start, ClassEntity end) throws WrongInheritanceException {
        if(start.getClass().getSimpleName().equals("ClassEntity") &&
        end.getClass().getSimpleName().equals("OtherClassEntity")) throw new WrongInheritanceException();

        if(start.getClass().getSimpleName().equals("OtherClassEntity") &&
                end.getClass().getSimpleName().equals("ClassEntity")) throw new WrongInheritanceException();

        if(start.getClass().getSimpleName().equals("OtherClassEntity") &&
                end.getClass().getSimpleName().equals("OtherClassEntity")) {
            Label labelStart = (Label) start.getVBox().getChildren().get(0);
            Label labelEnd = (Label) end.getVBox().getChildren().get(0);
            if(!labelStart.getText().equals("<<interface>>")
            && !labelEnd.getText().equals("<<interface>>")) throw new WrongInheritanceException();
        }
        if(start.getClass().getSimpleName().equals("OtherClassEntity") &&
                end.getClass().getSimpleName().equals("OtherClassEntity")) {
            Label labelStart = (Label) start.getVBox().getChildren().get(0);
            Label labelEnd = (Label) end.getVBox().getChildren().get(0);
            if (labelStart.getText().equals("<<enum>>") ||
                    labelEnd.getText().equals("<<enum>>")) throw new WrongInheritanceException();
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

    public void wrongAggregationOrComposition(ClassEntity start, ClassEntity end) throws WrongCompositionOrAggregation {
        Label label;
        if(end.getClass().getSimpleName().equals("OtherClassEntity")) {
            label = (Label) end.getVBox().getChildren().get(0);
            if(!label.getText().equals("<<interface>>")) throw new WrongCompositionOrAggregation();
        }
        if(start.getClass().getSimpleName().equals("OtherClassEntity"))
            throw new WrongCompositionOrAggregation();
    }


}
