# My paradigms Solutions

Second semester / Второй семестр программирования - парадигмы программирования

---

Тесты к заданиям и описания модификаций находятся в папке `paradigms-2022` (сабмодуль нашего преподавателя)

Скрипты для запуска тестов находятся в папке с заданиями.

---

</li></ol><p><a href="paradigms-2022">Репозиторий курса</a></p><h3 id="java-binary-search">Домашнее задание 2. Бинарный поиск</h3><ol><li>
            Реализуйте итеративный и рекурсивный варианты бинарного поиска в массиве.
        </li><li>
            На вход подается целое число <code>x</code> и массив целых чисел <code>a</code>,
            отсортированный по невозрастанию.
            Требуется найти минимальное значение индекса <code>i</code>,
            при котором <code>a[i] &lt;= x</code>.
        </li><li>
            Для <code>main</code>, функций бинарного поиска и вспомогательных функций 
            должны быть указаны, пред- и постусловия. 
            Для реализаций методов должны быть приведены
            доказательства соблюдения контрактов в терминах троек Хоара.
        </li><li>
            Интерфейс программы.
            <ul><li>Имя основного класса &mdash; <code>search.BinarySearch</code>.</li><li>Первый аргумент командной строки &mdash; число <code>x</code>.</li><li>Последующие аргументы командной строки &mdash; элементы массива <code>a</code>.</li></ul></li><li>
            Пример запуска: <code>java search.BinarySearch 3 5 4 3 2 1</code>.
            Ожидаемый результат: <code>2</code>.
        </li></ol><h3 id="java-array-queue">Домашнее задание 3. Очередь на массиве</h3><ol><li>
            Определите модель и найдите инвариант структуры данных
            &laquo;<a href="http://ru.wikipedia.org/wiki/%D0%9E%D1%87%D0%B5%D1%80%D0%B5%D0%B4%D1%8C_(%D0%BF%D1%80%D0%BE%D0%B3%D1%80%D0%B0%D0%BC%D0%BC%D0%B8%D1%80%D0%BE%D0%B2%D0%B0%D0%BD%D0%B8%D0%B5)">очередь</a>&raquo;.
            Определите функции, которые необходимы для реализации очереди.
            Найдите их пред- и постусловия, при условии что очередь не содержит <code>null</code>.
        </li><li>
            Реализуйте классы, представляющие <b>циклическую</b> очередь 
            на основе массива.
            <ul><li>
                    Класс <code>ArrayQueueModule</code> должен реализовывать один экземпляр
                    очереди с использованием переменных класса.
                </li><li>
                    Класс <code>ArrayQueueADT</code> должен реализовывать очередь в виде
                    абстрактного типа данных (с явной передачей ссылки на экземпляр очереди).
                </li><li>
                    Класс <code>ArrayQueue</code> должен реализовывать очередь в виде
                    класса (с неявной передачей ссылки на экземпляр очереди).
                </li><li>
                    Должны быть реализованы следующие функции (процедуры) / методы:
                    <ul><li><code>enqueue</code> &ndash; добавить элемент в очередь;</li><li><code>element</code> &ndash; первый элемент в очереди;</li><li><code>dequeue</code> &ndash; удалить и вернуть первый элемент в очереди;</li><li><code>size</code> &ndash; текущий размер очереди;</li><li><code>isEmpty</code> &ndash; является ли очередь пустой;</li><li><code>clear</code> &ndash; удалить все элементы из очереди.</li></ul></li><li>
                    Модель, инвариант, пред- и постусловия 
                    записываются в исходном коде в виде комментариев.
                </li><li>
                    Обратите внимание на инкапсуляцию данных и кода 
                    во всех трех реализациях.
                </li></ul></li><li>
            Напишите тесты к реализованным классам.
        </li></ol><h3 id="java-queues">Домашнее задание 4. Очереди</h3><ol><li>
            Определите интерфейс очереди <code>Queue</code> и опишите его контракт.
        </li><li>
            Реализуйте класс <code>LinkedQueue</code> &mdash; очередь на связном списке.
        </li><li>
            Выделите общие части классов <code>LinkedQueue</code>
            и <code>ArrayQueue</code> в базовый класс <code>AbstractQueue</code>.
        </li></ol><p>Это домашнее задание <em>связано</em> с предыдущим.</p><h3 id="java-tabulator">Домашнее задание 5. Вычисление в различных типах</h3><p>
        Добавьте в программу разбирающую и вычисляющую выражения 
        трех переменных поддержку вычисления в различных типах.
    </p><ol><li><p>
                Создайте класс <code>expression.generic.GenericTabulator</code>,
                реализующий интерфейс <code>expression.generic.Tabulator</code>:
            </p><pre>
    public interface Tabulator {
        Object[][][] tabulate(
            String mode, String expression, 
            int x1, int x2, int y1, int y2, int z1, int z2
        ) throws Exception;
    }
            </pre><p>Аргументы</p><ul><li><code>mode</code> &mdash; режим работы
                    <table><tr><th>Режим</th><th>Тип</th></tr><tr><td><code>i</code></td><td><code>int</code> с детекцией переполнений</td></tr><tr><td><code>d</code></td><td><code>double</code></td></tr><tr><td><code>bi</code></td><td><code><a href="https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/math/BigInteger.html">BigInteger</a></code></td></tr></table></li><li><code>expression</code> &mdash; вычисляемое выражение;
                </li><li><code>x1</code>, <code>x2</code>;
                    <code>y1</code>, <code>y2</code>;
                    <code>z1</code>, <code>z2</code> &mdash;
                    диапазоны изменения переменных (включительно).
                </li></ul><p>
                Возвращаемое значение &mdash; таблица значений функции, где
                <code>R[i][j][k]</code> соответствует 
                <code>x = x1 + i</code>, <code>y = y1 + j</code>, <code>z = z1 + k</code>.
                Если вычисление завершилось ошибкой, в соответствующей ячейке
                должен быть <code>null</code>.
            </p></li><li>
            Доработайте интерфейс командной строки:
            <ul><li>
                    Первым аргументом командной строки программа должна принимать указание
                    на тип, в котором будут производится вычисления:
                    <table><tr><th>Опция</th><th>Тип</th></tr><tr><td><code>-i</code></td><td><code>int</code> с детекцией переполнений</td></tr><tr><td><code>-d</code></td><td><code>double</code></td></tr><tr><td><code>-bi</code></td><td><code><a href="https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/math/BigInteger.html">BigInteger</a></code></td></tr></table></li><li>
                    Вторым аргументом командной строки программа должна принимать
                    выражение для вычисления.
                </li><li>
                    Программа должна выводить результаты вычисления для
                    всех целочисленных значений переменных из диапазона &minus;2..2.
                </li></ul></li><li>
            Реализация не должна содержать
            <a href="https://docs.oracle.com/javase/specs/jls/se17/html/jls-5.html#jls-5.1.9">непроверяемых преобразований типов</a>.
        </li><li>
            Реализация не должна использовать аннотацию
            <code><a href="https://docs.oracle.com/javase/specs/jls/se17/html/jls-9.html#jls-9.6.4.5">@SuppressWarnings</a></code>.
        </li><li>
            При выполнении задания следует обратить внимание на простоту добавления новых типов и операциий.
        </li></ol><h3 id="js-functional-expressions">Домашнее задание 6. Функциональные выражения на JavaScript</h3><ol><li>
            Разработайте функции <code>cnst</code>, <code>variable</code>,
            <code>add</code>, <code>subtract</code>, <code>multiply</code>,
            <code>divide</code>, <code>negate</code>
            для вычисления выражений с тремя переменными: 
            <code>x</code>, <code>y</code> и <code>z</code>.
        </li><li>
            Функции должны позволять производить вычисления вида:
            <pre>
let expr = subtract(
    multiply(
        cnst(2),
        variable("x")
    ),
    cnst(3)
);

println(expr(5, 0, 0));
            </pre>
            При вычислении выражения вместо каждой переменной подставляется значение,
            переданное в качестве соответствующего параметра функции 
            <code>expr</code>. 
            Таким образом, результатом вычисления 
            приведенного примера должно быть число 7.
        </li><li>
            Тестовая программа должна вычислять выражение
            <code>x<sup>2</sup>&minus;2x+1</code>, для <code>x</code> от 0 до 10.
        </li><li><b>Сложный вариант.</b> Требуется дополнительно написать функцию
            <code>parse</code>, осуществляющую разбор выражений,
            записанных в
            <a href="https://ru.wikipedia.org/wiki/%D0%9E%D0%B1%D1%80%D0%B0%D1%82%D0%BD%D0%B0%D1%8F_%D0%BF%D0%BE%D0%BB%D1%8C%D1%81%D0%BA%D0%B0%D1%8F_%D0%B7%D0%B0%D0%BF%D0%B8%D1%81%D1%8C">обратной польской записи</a>.
            Например, результатом
            <pre>parse("x x 2 - * x * 1 +")(5, 0, 0)</pre>
            должно быть число <code>76</code>.
        </li><li>
            При выполнении задания следует обратить внимание на:
            <ul><li>Применение функций высшего порядка.</li><li>Выделение общего кода для операций.</li></ul></li></ol><h3 id="js-object-expressions">Домашнее задание 7. Объектные выражения на JavaScript</h3><ol><li>
            Разработайте классы <code>Const</code>, <code>Variable</code>,
            <code>Add</code>, <code>Subtract</code>, <code>Multiply</code>,
            <code>Divide</code>, <code>Negate</code>
            для представления выражений с тремя переменными: 
            <code>x</code>, <code>y</code> и <code>z</code>.
            <ol><li>
                    Пример описания выражения <code>2x-3</code>:
                    <pre>
let expr = new Subtract(
    new Multiply(
        new Const(2),
        new Variable("x")
    ),
    new Const(3)
);

println(expr.evaluate(5, 0, 0));
                    </pre></li><li>
                    При вычислении такого выражения вместо каждой переменной
                    подставляется её значение, переданное в качестве аргумента
                    метода <code>evaluate</code>.
                    Таким образом, результатом вычисления приведенного примера 
                    должно стать число 7.
                </li><li>
                    Метод <code>toString()</code> должен выдавать
                    запись выражения в
                    <a href="https://ru.wikipedia.org/wiki/%D0%9E%D0%B1%D1%80%D0%B0%D1%82%D0%BD%D0%B0%D1%8F_%D0%BF%D0%BE%D0%BB%D1%8C%D1%81%D0%BA%D0%B0%D1%8F_%D0%B7%D0%B0%D0%BF%D0%B8%D1%81%D1%8C">обратной польской записи</a>.
                    Например, <code>expr.toString()</code> должен выдавать
                    &laquo;<code>2 x * 3 -</code>&raquo;.
                </li></ol></li><li>
            Функция <code>parse</code> должна выдавать разобранное
            объектное выражение.
        </li><li><b>Сложный вариант.</b><div>
            Метод <code>diff("x")</code> должен возвращать выражение,
            представляющее производную исходного выражения
            по переменной <code>x</code>.
            Например, <code>expr.diff("x")</code> должен возвращать
            выражение, эквивалентное <code>new Const(2)</code>
            (выражения
                <code>new Subtract(new Const(2), new Const(0))</code> и
                <pre>
new Subtract(
    new Add(
        new Multiply(new Const(0), new Variable("x")),
        new Multiply(new Const(2), new Const(1))
    )
    new Const(0)
)
                 </pre>
                 так же будут считаться правильным ответом).
            </div></li><li><b>Бонусный вариант.</b>
            Требуется написать
            метод <code>simplify()</code>, производящий вычисления
            константных выражений. Например,
            <pre>parse("x x 2 - * 1 *").diff("x").simplify().toString()</pre>
            должно возвращать &laquo;<code>x x 2 - +</code>&raquo;.
        </li><li>
            При выполнении задания следует обратить внимание на:
            <ul><li>Применение инкапсуляции.</li><li>Выделение общего кода для операций.</li><li>Минимизацию необходимой памяти.</li></ul></li></ol><h3 id="js-expression-parsing">Домашнее задание 8. Обработка ошибок на JavaScript</h3><ol><li>
            Добавьте в предыдущее домашнее задание функцию
            <code>parsePrefix(string)</code>, разбирающую выражения,
            задаваемые записью вида &laquo;<code>(- (* 2 x) 3)</code>&raquo;.
            Если разбираемое выражение некорректно, метод
            <code>parsePrefix</code> должен бросать
            человеко-читаемое сообщение об ошибке.
        </li><li>
            Добавьте в предыдущее домашнее задание метод
            <code>prefix()</code>, выдающий выражение в формате,
            ожидаемом функцией <code>parsePrefix</code>.
        </li><li>
            При выполнении задания следует обратить внимание на:
            <ul><li>Применение инкапсуляции.</li><li>Выделение общего кода для операций.</li><li>Минимизацию необходимой памяти.</li><li>Обработку ошибок.</li></ul></li></ol><h3 id="clojure-linear">Домашнее задание 9. Линейная алгебра на Clojure</h3><ol><li>
            Разработайте функции для работы с объектами линейной алгебры,
            которые представляются следующим образом:
            <ul><li>скаляры &ndash; числа</li><li>векторы &ndash; векторы чисел;</li><li>матрицы &ndash; векторы векторов чисел.</li></ul></li><li>
            Функции над векторами:
            <ul><li><code>v+</code>/<code>v-</code>/<code>v*</code>/<code>vd</code>
                    &ndash; покоординатное сложение/вычитание/умножение/деление;
                </li><li><code>scalar</code>/<code>vect</code>
                    &ndash; скалярное/векторное произведение;
                </li><li><code>v*s</code>
                    &ndash; умножение на скаляр.
                </li></ul></li><li>
            Функции над матрицами:
            <ul><li><code>m+</code>/<code>m-</code>/<code>m*</code>/<code>md</code>
                    &ndash; поэлементное сложение/вычитание/умножение/деление;
                </li><li><code>m*s</code> &ndash; умножение на скаляр;
                </li><li><code>m*v</code> &ndash; умножение на вектор;
                </li><li><code>m*m</code> &ndash; матричное умножение;
                </li><li><code>transpose</code> &ndash; транспонирование;
                </li></ul></li><li><b>Сложный вариант.</b><ol><li>
                    Ко всем функциям должны быть указаны контракты.
                    Например, нельзя складывать вектора разной длины.
                </li><li>
                    Все функции должны поддерживать произвольное число аргументов.
                    Например
                    <code>(v+ [1 2] [3 4] [5 6])</code> должно быть равно
                    <code>[9 12]</code>.
                </li></ol></li><li>
            При выполнении задания следует обратить внимание на:
            <ul><li>
                    Применение функций высшего порядка.
                </li><li>
                    Выделение общего кода для операций.
                </li></ul></li><li><b>Code Golf</b><p>Правила</p><ol><li>
                    Выигрывает самая короткая программа.
                    Длина программы считается после удаления незначимых пробелов.
                </li><li>
                    Можно использовать произвольные функции 
                    <a href="https://clojure.org/api/cheatsheet">стандартной библиотеки</a>
                    Clojure.
                </li><li>
                    Нельзя использовать функции Java и внешних библиотек.
                </li><li>
                    Подача решений через 
                    <a href="https://t.me/+xdzpiSGIUUQ3NGIy">чат</a>.
                    Решение должно быть корректно отформатировано
                    и начинаться с <code>;Solution номинация длина</code>.
                    Например, <code>;Solution det-2 1000</code>.
                </li></ol><p>Номинации</p><ul><li><code>det-3</code> &mdash; определитель матрицы за <var>O(n&sup3;)</var>;</li><li><code>det-s</code> &mdash; определитель дольше чем за <var>O(n&sup3;)</var>;</li><li><code>inv-3</code> &mdash; обратная матрица за <var>O(n&sup3;)</var>;</li><li><code>inv-s</code> &mdash; обратная дольше чем за <var>O(n&sup3;)</var>.</li></ul></li></ol><h3 id="clojure-functional-expressions">Домашнее задание 10. Функциональные выражения на Clojure</h3><ol><li>
            Разработайте функции
            <code>constant</code>,
            <code>variable</code>,
            <code>add</code>,
            <code>subtract</code>,
            <code>multiply</code>,
            <code>divide</code> и
            <code>negate</code>
            для представления арифметических выражений.
            <ol><li>
                    Пример описания выражения <code>2x-3</code>:
                    <pre>
(def expr
  (subtract
    (multiply
      (constant 2)
      (variable "x"))
    (constant 3)))
                    </pre></li><li>
                    Выражение должно быть функцией, возвращающей
                    значение выражения при подстановке переменных,
                    заданных отображением.
                    Например, <code>(expr {"x" 2})</code> должно быть равно 1.
                </li></ol></li><li>
            Разработайте разборщик выражений, читающий
            выражения в стандартной для Clojure форме.
            Например, <pre>(parseFunction "(- (* 2 x) 3)")</pre>
            должно быть эквивалентно <code>expr</code>.
        </li><li><b>Сложный вариант.</b>
            Функции <code>add</code>, <code>subtract</code>,
            <code>multiply</code> и <code>divide</code>
            должны принимать произвольное число аргументов.
            Разборщик так же должен допускать произвольное число аргументов для 
            <code>+</code>, <code>-</code>, <code>*</code>, <code>/</code>.
        </li><li>
            При выполнении задания следует обратить внимание на:
            <ul><li>
                    Выделение общего кода для операций.
                </li></ul></li></ol><h3 id="clojure-object-expressions">Домашнее задание 11. Объектные выражения на Clojure</h3><ol><li>
            Разработайте конструкторы
            <code>Constant</code>,
            <code>Variable</code>,
            <code>Add</code>,
            <code>Subtract</code>,
            <code>Multiply</code>,
            <code>Divide</code> и
            <code>Negate</code>
            для представления арифметических выражений.
            <ol><li>
                    Пример описания выражения <code>2x-3</code>:
                    <pre>
(def expr
  (Subtract
    (Multiply
      (Constant 2)
      (Variable "x"))
    (Constant 3)))
                    </pre></li><li>
                    Функция <code>(evaluate expression vars)</code>
                    должна производить вычисление выражения
                    <code>expression</code> для значений
                    переменных, заданных отображением <code>vars</code>.
                    Например, <code>(evaluate expr {"x" 2})</code>
                    должно быть равно 1.
                </li><li>
                    Функция <code>(toString expression)</code>
                    должна выдавать запись выражения в
                    стандартной для Clojure форме.
                </li><li>
                    Функция <code>(parseObject "expression")</code>
                    должна разбирать выражения, записанные
                    в стандартной для Clojure форме.
                    Например, <pre>(parseObject "(- (* 2 x) 3)")</pre>
                    должно быть эквивалентно <code>expr</code>.
                </li><li>
                    Функция <code>(diff expression "variable")</code>
                    должна возвращать выражение,
                    представляющее производную исходного выражения
                    по заданой перемененной.
                    Например, <code>(diff expression "x")</code>
                    должен возвращать выражение, эквивалентное
                    <code>(Constant 2)</code>, при этом
                    выражения
                    <code>(Subtract (Constant 2) (Constant 0))</code> и
                    <pre>
(Subtract
  (Add
    (Multiply (Constant 0) (Variable "x"))
    (Multiply (Constant 2) (Constant 1)))
  (Constant 0))
                    </pre>
                    так же будут считаться правильным ответом.
                </li></ol></li><li><b>Сложный вариант.</b>
            Конструкторы <code>Add</code>, <code>Subtract</code>,
            <code>Multiply</code> и <code>Divide</code>
            должны принимать произвольное число аргументов.
            Разборщик так же должен допускать произвольное число
            аргументов для <code>+</code>, <code>-</code>, <code>*</code>, <code>/</code>.
        </li><li>
            При выполнении задания можно использовать функции,
            приведённые на лекции.
        </li><li>
            При выполнении задания можно использовать
            любой способ преставления объектов.
        </li></ol><h3 id="clojure-expression-parsing">Домашнее задание 12. Комбинаторные парсеры</h3><ol><li><b>Простой вариант.</b>
            Реализуйте функцию <code>(parseObjectSuffix "expression")</code>,
            разбирающую выражения, записанные в суффиксной форме,
            и функцию <code>toStringSuffix</code>,
            возвращающую строковое представление выражения в этой форме.
            Например, <pre>(toStringSuffix (parseObjectSuffix "( ( 2 x * ) 3 - )"))</pre>
            должно возвращать <code>((2 x *) 3 -)</code>.
        </li><li><b>Сложный вариант.</b>
            Реализуйте функцию <code>(parseObjectInfix "expression")</code>,
            разбирающую выражения, записанные в инфиксной форме,
            и функцию <code>toStringInfix</code>,
            возвращающую строковое представление выражения в этой форме.
            Например, <pre>(toStringInfix (parseObjectInfix "2 * x - 3"))</pre>
            должно возвращать <code>((2 * x) - 3)</code>.
        </li><li><b>Бонусный вариант.</b>
            Добавьте в библиотеку комбинаторов возможность обработки ошибок
            и продемонстрируйте ее использование в вашем парсере.
        </li><li>
            Функции разбора должны базироваться на библиотеке
            комбинаторов, разработанной на лекции.
        </li></ol><h3 id="prolog-primes">Домашнее задание 13. Простые числа на Prolog</h3><ol><li><p>
                Разработайте правила:
                <ul><li><code>prime(N)</code>,
                        проверяющее, что <code>N</code> &ndash; простое число.
                    </li><li><code>composite(N)</code>,
                        проверяющее, что <code>N</code> &ndash; составное число.
                    </li><li><code>prime_divisors(N, Divisors)</code>,
                        проверяющее, что список <code>Divisors</code>
                        содержит все простые делители числа <code>N</code>,
                        упорядоченные по возрастанию.
                        Если <code>N</code> делится на простое число <code>P</code>
                        несколько раз, то <code>Divisors</code> должен содержать
                        соответствующее число копий <code>P</code>.
                    </li></ul></p></li><li><p>Варианты</p><ul><li>Простой: <code>N</code> &le; 1000.</li><li>Сложный: <code>N</code> &le; 10<sup>5</sup>.</li><li>Бонусный: <code>N</code> &le; 10<sup>7</sup>.</li></ul></li><li>
            Вы можете рассчитывать, на то, что до первого запроса будет
            выполнено правило <code>init(MAX_N)</code>.
        </li></ol><h3 id="prolog-map">Домашнее задание 14. Деревья поиска на Prolog</h3><ol><li>
            Реализуйте ассоциативный массив (map) на основе деревьев поиска.
            Для решения можно реализовать любое дерево поиска логарифмической высоты.
        </li><li><p><b>Простой вариант.</b>
                Разработайте правила:
            </p><ul><li><code>map_build(ListMap, TreeMap)</code>,
                    строящее дерево из упорядоченного списка пар
                    ключ-значение (O(<i>n</i>));
                </li><li><code>map_get(TreeMap, Key, Value)</code>,
                    проверяющее, что массив содержит заданную
                    пару ключ-значение (O(log <i>n</i>)).
                </li></ul></li><li><p><b>Сложный вариант.</b>
                Дополнительно разработайте правила:
            </p><ul><li><code>map_put(TreeMap, Key, Value, Result)</code>;
                    добавляющее пару ключ-значение в массив,
                    или заменяющее текущее значение для ключа
                    (O(log <i>n</i>));
                </li><li><code>map_remove(TreeMap, Key, Result)</code>
                    удаляющее отображение для ключа
                    (O(log <i>n</i>));
                </li><li><code>map_build(ListMap, TreeMap)</code>,
                    строящее дерево из <b>не</b>упорядоченного списка пар
                    ключ-значение
                    (O(<i>n</i> log <i>n</i>)).
                </li></ul></li></ol><h3 id="prolog-expression-parsing">Домашнее задание 15. Разбор выражений на Prolog</h3><ol><li>
            Доработайте правило <code>eval(Expression, Variables, Result)</code>,
            вычисляющее арифметические выражения.
            <ol><li>
                    Пример вычисления выражения <code>2x-3</code> для <code>x = 5</code>: 
                    <pre>
eval(
    operation(op_subtract,
        operation(op_multiply,
            const(2),
            variable(x)
        ),
        const(3)
    ),
    [(x, 5)],
    7
)
                    </pre></li><li>
                    Поддерживаемые операции:
                    сложение  (<code>op_add</code>,      <code>+</code>),
                    вычитание (<code>op_subtract</code>, <code>-</code>),
                    умножение (<code>op_multiply</code>, <code>*</code>),
                    деление   (<code>op_divide</code>,   <code>/</code>),
                    противоположное число(<code>op_negate</code>, <code>negate</code>).
                </li></ol></li><li><b>Простой вариант.</b>
            Реализуйте правило <code>suffix_str(Expression, Atom)</code>,
            разбирающее/выводящее выражения, записанные в суффиксной форме.
            Например, 
<pre>
    suffix_str(
        operation(op_subtract,operation(op_multiply,const(2),variable(x)),const(3)),
        '((2 x *) 3 -)'
    )
</pre>

</li><li><b>Сложный вариант.</b>
            Реализуйте правило <code>infix_str(Expression, Atom)</code>,
            разбирающее/выводящее выражения, записанные в полноскобочной инфиксной форме.
            Например, 
<pre>
    infix_str(
        operation(op_subtract,operation(op_multiply,const(2),variable(x)),const(3)),
        '((2 * x) - 3)'
    )
</pre></li><li>
            Правила должны быть реализованы с применением DC-грамматик.   
            </li>
