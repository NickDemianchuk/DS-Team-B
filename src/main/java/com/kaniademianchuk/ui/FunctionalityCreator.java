package com.kaniademianchuk.ui;

public class FunctionalityCreator {

//    private final Scanner reader;
//
//    public FunctionalityCreator(Scanner reader) {
//        this.reader = reader;
//    }
//
//    public List<ITogglable> defaultDialog() {
//        return this.functionalityDialog(new ArrayList<>());
//    }
//
//    public List<ITogglable> dialogWithInitial(List<ITogglable> initial) {
//        return this.functionalityDialog(new ArrayList<>(initial));
//    }
//
//    private List<ITogglable> functionalityDialog(List<ITogglable> functionalities) {
//        while (true) {
//            System.out.format("Current functionalities: %s\n", functionalities);
//            System.out.println("Choose: add, edit, remove, done");
//            String n = reader.nextLine();
//            switch (n) {
//                case "done":
//                    return functionalities;
//                case "add":
//                    addDialog(functionalities);
//                    break;
//                case "edit":
//                    editDialog(functionalities);
//                    break;
//                case "remove":
//                    removeDialog(functionalities);
//                    break;
//            }
//        }
//    }
//
//    private void editDialog(List<ITogglable> functionalities) {
//        System.out.print("Functionality name: ");
//        String editName = reader.nextLine();
//        if (editName.length() == 0) {
//            return;
//        }
//        int index = IntStream.range(0, functionalities.size())
//                .filter(userInd -> functionalities.get(userInd).getName().equals(editName))
//                .findFirst().orElse(-1);
//        if (index == -1) {
//            System.out.format("Functionality with name %s doesn't exist\n", editName);
//            return;
//        }
//        ITogglable newValue = editValuesDialog(functionalities.get(index));
//        functionalities.set(index, newValue);
//    }
//
//    private ITogglable editValuesDialog(ITogglable old) {
//        ITogglable newValue = old;
//
//        System.out.format("Functionality name(%s): ", old.getName());
//        String newName = reader.nextLine();
//        if (newName.length() != 0) {
//            newValue = newValue.setName(newName);
//        }
//        System.out.format("isOn(%s): ", old.isOn());
//        boolean isOn = reader.nextBoolean();
//        reader.nextLine();
//        newValue = isOn ? newValue.turnOn() : newValue.turnOff();
//        return newValue;
//    }
//
//    private void addDialog(List<ITogglable> functionalities) {
//        System.out.print("Functionality name: ");
//        String toggleName = reader.nextLine();
//        if (toggleName.length() == 0) {
//            return;
//        }
//        System.out.print("isOn: ");
//        boolean isOn = reader.nextBoolean();
//        reader.nextLine();
//        functionalities.add(new DefaultTogglable(toggleName, isOn));
//    }
//
//    private void removeDialog(List<ITogglable> functionalities) {
//        System.out.print("Name to be deleted: ");
//        String removeName = reader.nextLine();
//        if (removeName.length() == 0) {
//            return;
//        }
//        functionalities.removeIf(iTogglable -> iTogglable.getName().equals(removeName));
//    }
}
