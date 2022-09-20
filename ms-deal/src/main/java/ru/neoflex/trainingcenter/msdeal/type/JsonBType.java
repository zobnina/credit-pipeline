package ru.neoflex.trainingcenter.msdeal.type;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;
import ru.neoflex.trainingcenter.msdeal.exception.UserTypeConvertException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;

public abstract class JsonBType implements UserType {

    final ObjectMapper mapper = JsonMapper.builder().findAndAddModules().build();

    @Override
    public int[] sqlTypes() {

        return new int[]{Types.JAVA_OBJECT};
    }

    @Override
    public Class<?> returnedClass() {

        return null;
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {

        return Objects.equals(x, y);
    }

    @Override
    public int hashCode(Object x) throws HibernateException {

        return x != null ? x.hashCode() : 0;
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {

        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(value);
            oos.flush();
            oos.close();
            bos.close();
            ByteArrayInputStream bais = new ByteArrayInputStream(bos.toByteArray());
            Object obj = new ObjectInputStream(bais).readObject();
            bais.close();

            return obj;
        } catch (ClassNotFoundException | IOException ex) {

            throw new HibernateException(ex);
        }
    }

    @Override
    public boolean isMutable() {

        return false;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {

        Object deepCopy = deepCopy(value);
        if (!(deepCopy instanceof Serializable)) {

            return (Serializable) deepCopy;
        }

        return null;
    }

    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {

        return deepCopy(cached);
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {

        return deepCopy(original);
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner) throws HibernateException, SQLException {

        final String cellContent = rs.getString(names[0]);
        if (cellContent == null) {

            return null;
        }
        try {

            return mapper.readValue(cellContent.getBytes(StandardCharsets.UTF_8), returnedClass());
        } catch (final Exception ex) {

            throw new UserTypeConvertException(ex);
        }
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session) throws HibernateException, SQLException {

        if (value == null) {
            st.setNull(index, Types.OTHER);

            return;
        }
        try {
            final StringWriter w = new StringWriter();
            mapper.writeValue(w, value);
            w.flush();
            st.setObject(index, w.toString(), Types.OTHER);
        } catch (final Exception ex) {

            throw new UserTypeConvertException(ex);
        }
    }
}
