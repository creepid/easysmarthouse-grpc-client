/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.creepid.grpc.client.serialization;

import com.github.creepid.grpc.client.StringTableAware;
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author rusakovich
 */
public class LinkedListSerializer extends AbstractSerializer {

    @Override
    boolean match(Class<?> cls) {
        return (cls == LinkedList.class);
    }

    @Override
    public Object decode(List<String> stringTable, ArrayDeque<String> indexes) {
        //class
        indexes.removeFirst();
        //list size
        indexes.removeFirst();
        
        LinkedList list = new LinkedList();

        //try to parse elements
        String elementClassIndex = null;
        while ((elementClassIndex = indexes.peek()) != null) {
            String elementClass = stringTable.get(Integer.parseInt(elementClassIndex) - 1);

            SerializedInstanceReference ref = SerializabilityUtil.decodeSerializedInstanceReference(elementClass);
            String name = ref.getName();
            if (!ref.getSignature().isEmpty()) {
                Serializer serializer = SerializerContainer.findSerializerForClass(ref.getName());
                list.add(serializer.decode(stringTable, indexes));
            } else {
                list.add(name);
            }

        }

        return list;
    }

    @Override
    public void encodeValue(Object value, Class<?> methodType, StringTableAware strTableAware) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void encodeType(Class<?> methodType, Class<?> runtimeType, StringTableAware strTableAware) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
