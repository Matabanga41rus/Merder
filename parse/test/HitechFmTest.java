/*
@Test – определяет что метод method() является тестовым.
@Before – указывает на то, что метод будет выполнятся перед каждым тестируемым методом @Test.
@After – указывает на то что метод будет выполнятся после каждого тестируемого метода @Test
@BeforeClass – указывает на то, что метод будет выполнятся в начале всех тестов,
а точней в момент запуска тестов(перед всеми тестами @Test).
@AfterClass – указывает на то, что метод будет выполнятся после всех тестов.
@Ignore – говорит, что метод будет проигнорирован в момент проведения тестирования.
(expected = Exception.class) – указывает на то, что в данном тестовом методе
вы преднамеренно ожидаете Exception.
(timeout = 100) – указывает, что тестируемый метод не должен занимать больше чем 100 миллисекунд.

Основные методы класса Assert для проверки:

fail(String) – указывает на то что бы тестовый метод завалился при этом выводя текстовое сообщение.
assertTrue([message], boolean condition) – проверяет, что логическое условие истинно.
assertsEquals([String message], expected, actual) – проверяет, что два значения совпадают.
Примечание: для массивов проверяются ссылки, а не содержание массивов.
assertNull([message], object) – проверяет, что объект является пустым null.
assertNotNull([message], object) – проверяет, что объект не является пустым null.
assertSame([String], expected, actual) – проверяет, что обе переменные относятся к одному объекту.
assertNotSame([String], expected, actual) – проверяет, что обе переменные относятся к разным объектам.
 */

import org.junit.Before;
import org.junit.Test;
import parseNews.CopyHitechFmRu;
import parseNews.HitechFmRu;

import java.io.IOException;

public class HitechFmTest {

    @Before
    public void parsePage(){
        CopyHitechFmRu hitechFmRu = new CopyHitechFmRu();
    }

    @Test
    public boolean searchNews(){

    }

    @Test
    public boolean searchNews(String tags){

    }
}
