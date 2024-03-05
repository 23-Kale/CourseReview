package BusinessLogicLayer;

public class Course {
    String department;
    int catalogNumber;

    public Course(String department, int catalogNumber) {
        if (isValidDepartment(department) && isValidCatalogNumber(catalogNumber)) {
            this.department = department;
            this.catalogNumber = catalogNumber;
        }
        else {
            throw new IllegalArgumentException("Invalid department or catalog number");
        }
    }

    private static boolean isValidDepartment(String department) {
        for (char c : department.toCharArray()) {
            if (Character.isLetter(c) && Character.isLowerCase(c) || Character.isSpaceChar(c)) {
                return false;
            }
        }
        return department.length() <= 4;
    }

    private static boolean isValidCatalogNumber(int catalogNumber) {
        return Integer.toString(catalogNumber).length() == 4;
    }

    public String getDepartment() {
        return department;
    }

    public int getCatalogNumber() {
        return catalogNumber;
    }

    public String toString(){
        return department.toUpperCase() + " " + catalogNumber;
    }
}
