A custom data structure with a deep iterator that can return subsequent
nested items <br />

### Difference between ArrayList(Java dataStructure) and our custom DeepArrayList

```ArrayList subList = new ArrayList();```<br />
```subList.add(3);```<br />
```subList.add(4);```<br />
```ArrayList arrayList = new ArrayList()```<br />
```arrayList.add(1)```<br />
```arrayList.dd(2)```<br />
```arrayList.add(subList)```<br />
```list.add(5)```<br />

```DeepArrayList deepArrayList = new DeepArrayList()```<br />
```deepArrayList.add(1)```<br />
```deepArrayList.add(2)```<br />
```deepArrayList.add(subList)```<br />
```deepArrayList.add(5)```<br />

Iterating over these elements would result in:<br />
for ArrayList: ```[1, 2, [3, 4], 5]```<br />
for DeepArrayList: ```[1, 2, 3, 4, 5]```
