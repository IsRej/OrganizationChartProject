import java.util.Optional;

public abstract class Component {
    protected String name;
    public Component(String name) {
        this.name = name;
    }
    public void add(Component c) {
        throw new UnsupportedOperationException("Cannot add to this component");
    }
    public abstract void print(int indent);
    public abstract Optional<Group> findGroup(String groupName);
    public abstract boolean removeWorker(String workerName);
}