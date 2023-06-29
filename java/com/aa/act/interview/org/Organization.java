package com.aa.act.interview.org;

import java.util.Optional;
import java.util.UUID;

public abstract class Organization {

    private Position root;
    
    public Organization() {
        root = createOrganization();
    }
    
    protected abstract Position createOrganization();
    
    /**
     * hire the given person as an employee in the position that has that title
     * 
     * @param person
     * @param title
     * @return the newly filled position or empty if no position has that title
     */
    public Optional<Position> hire(Name person, String title) {
        
    	return checkDirectReports(root, person, title);
   
    }

    private Optional<Position> checkDirectReports(Position pos, Name person, String title) {

    	if (!pos.isFilled() && title == pos.getTitle()) {
	    	Employee emp = new Employee(generateUniqueId(),person);
	        pos.setEmployee(Optional.of(emp));
	        return Optional.of(pos);
    	} else {
	    	for (Position p : pos.getDirectReports()) {
	    		Optional<Position> filledPosition = checkDirectReports(p, person, title);
	    		if (filledPosition.isPresent()) return filledPosition;
	    	}
    	}
    	return Optional.empty();
    }
    
    private static int generateUniqueId() {
    	UUID idOne = UUID.randomUUID();
        String str=""+idOne;        
        int uid=str.hashCode();
        String filterStr=""+uid;
        str=filterStr.replaceAll("-", "");
        return Integer.parseInt(str);
    }
    
    @Override
    public String toString() {
        return printOrganization(root, "");
    }
    
    private String printOrganization(Position pos, String prefix) {
        StringBuffer sb = new StringBuffer(prefix + "+-" + pos.toString() + "\n");
        for(Position p : pos.getDirectReports()) {
            sb.append(printOrganization(p, prefix + "  "));
        }
        return sb.toString();
    }
}
