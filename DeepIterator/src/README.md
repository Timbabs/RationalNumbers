A custom data structure with a deep iterator that can return subsequent
nested items <br />

### Difference between ArrayList(Java dataStructure) and our custom DeepArrayList

```ArrayList subList = new ArrayList();```<br />
```subList.add(3);```<br />
```subList.add(4);```<br />
```ArrayList list = new ArrayList()```<br />
```list.add(1)```<br />
```list.dd(2)```<br />
```list.add(subList)```<br />
```list.add(5)```<br />

Iterating over these elements would result in:<br />
for ArrayList: ```[1, 3, [3, 4], 5]```<br />
for DeepArrayList: ```[1, 2, 3, 4, 5]```
