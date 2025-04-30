import java.util.Optional;

public class Worker extends Component {
    public Worker(String name) {
        super(name);
    }
    @Override
    public void print(int indent) {
        System.out.println("  ".repeat(indent) + name);
    }
    @Override
    public Optional<Group> findGroup(String groupName) {
        return Optional.empty();
    }
    @Override
    public boolean removeWorker(String workerName) {
        return false;
    }
}