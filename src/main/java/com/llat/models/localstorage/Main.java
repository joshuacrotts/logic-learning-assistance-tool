package com.llat.models.localstorage;

import com.llat.models.localstorage.uidescription.UIDescriptionAdaptor;
import com.llat.models.localstorage.uidescription.UIDescriptionObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        UIDescriptionObject uido = (UIDescriptionObject) new UIDescriptionAdaptor().getData();
//        Field[] fields = uido.getClass().getDeclaredFields();
        Method[] methods = uido.getClass().getMethods();

        for (Method method : methods)
            System.out.println(method.getName());

//        for (Field field : fields) {
//            System.out.println(field.getName() + ", " + field);
//        }
        Field field = uido.getClass().getDeclaredField("implication");
        field.setAccessible(true);
        Object value = field.get(uido);

    }

    private void translateUIDO(UIDescriptionObject obj) {
        String axiomsExample = obj.getImplication().getAxioms().getExample();
//        Translate axiomsExample
        obj.getImplication().getAxioms().setExample("sfsdfs");
        String axiomsExplanation = obj.getImplication().getAxioms().getExplanation();


        UIDescriptionAdaptor uida = new UIDescriptionAdaptor();
        uida.update(obj);
    }
}
