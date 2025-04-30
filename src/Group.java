import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class Group extends Component {
    private List<Component> children = new ArrayList<>();
    public Group(String groupName, String bossName) {
        super(groupName + " (Boss: " + bossName + ")");
    }
    @Override
    public void add(Component c) {
        children.add(c);
    }
    @Override
    public void print(int indent) {
        System.out.println("  ".repeat(indent) + name);
        for (Component c : children) {
            c.print(indent + 1);
        }
    }
    @Override
    public Optional<Group> findGroup(String groupName) {
        String baseName = name.split("\\s*\\(Boss:")[0];
        if (baseName.equals(groupName)) {
            return Optional.of(this);
        }
        for (Component c : children) {
            Optional<Group> result = c.findGroup(groupName);
            if (result.isPresent()) return result;
        }
        return Optional.empty();
    }
    @Override
    public boolean removeWorker(String workerName) {
        Iterator<Component> it = children.iterator();
        while (it.hasNext()) {
            Component c = it.next();
            if (c instanceof Worker && c.name.equals(workerName)) {
                it.remove();
                return true;
            }
            if (c instanceof Group && ((Group)c).removeWorker(workerName)) {
                return true;
            }
        }
        return false;
    }
}