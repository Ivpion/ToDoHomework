import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import dao.Dao;
import dao.ToDoModelDao;
import exception.AppDBException;
import model.ToDoModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utils.JsonUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class ToDoModelDaoTests {

    private Dao toDoModelDao;

    private String testDaoPath = "txtDB.json";
    private ToDoModel toDoModel1;

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private Type toDoModelType = new TypeToken<Map<Integer,ToDoModel>>(){}.getType();


    @Before
    public void setUp(){
        testDaoPath = new File(ToDoModelDaoTests.class.getResource(testDaoPath).getFile()).getAbsolutePath();
        try {
            new File(testDaoPath).createNewFile();
            String initialJson  = "[{\"id\": 1,\"title\": \"asdf\",\"isCompleted\": false}]";
            JsonUtils.writeJsonToFile(testDaoPath, initialJson);

        } catch (IOException e) {
            e.printStackTrace();
        }
        toDoModelDao = new ToDoModelDao(testDaoPath);
        toDoModel1 = new ToDoModel("asdf", false);
    }

    @After
    public void tearDown(){
        testDaoPath = "txtDB.json";
        //String toDoListJson = gson.toJson(new ArrayList<>(), toDoModelType);
        //JsonUtils.writeJsonToFile(testDaoPath, toDoListJson);
        new File(testDaoPath).delete();
    }

    @Test
    public void positiveCreateTest() throws AppDBException {
        ToDoModel expected = new ToDoModel("asdf", false);
        expected.setId(1);
        ToDoModel actual = toDoModelDao.create(toDoModel1);
        assertEquals(expected, actual);
    }

    @Test
    public void negativeCreateTest() throws AppDBException {

        ToDoModel expected = new ToDoModel("asdf", false);
        expected.setId(100500);
        ToDoModel actual = toDoModelDao.create(toDoModel1);
        assertNotEquals(expected, actual);
        assertNotNull(actual);
    }

    @Test
    public void positiveReadTest(){
        ToDoModel expected = new ToDoModel("asdf", false);
        expected.setId(1);



        ToDoModel actual = toDoModelDao.read(1);
        assertEquals(expected, actual);
    }

    @Test
    public void negativeReadTest(){
        ToDoModel expected = new ToDoModel("asdf", false);
        expected.setId(1);
        ToDoModel actual = toDoModelDao.read(5);
        assertNotEquals(expected, actual);
       // assertNotNull(actual);
    }

    @Test
    public void positiveUpdateTest() throws AppDBException {
        ToDoModel expected = new ToDoModel("asdf", true);
        expected.setId(1);
        toDoModel1.setId(1);
        ToDoModel actual = toDoModelDao.update(toDoModel1);
        assertEquals(expected, actual);
    }

    @Test(expected = AppDBException.class)
    public void negativeUpdateTest() throws AppDBException {
        ToDoModel expected = new ToDoModel("asdf", true);
        expected.setId(1);
        toDoModel1.setId(1000);
        ToDoModel actual = toDoModelDao.update(toDoModel1);


    }

    @Test
    public void positiveDeleteTest() throws AppDBException {
        ToDoModel expected = new ToDoModel("asdf", false);
        expected.setId(1);
        toDoModel1.setId(1);
        ToDoModel actual = toDoModelDao.delete(toDoModel1);
        assertEquals(expected, actual);
    }

    @Test(expected = AppDBException.class)
    public void negativeDeleteTest() throws AppDBException {
        ToDoModel expected = new ToDoModel("asdf", true);
        expected.setId(1);
        ToDoModel actual = toDoModelDao.delete(toDoModel1);

    }


}
