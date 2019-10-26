A custom data structure with a deep iterator that can return subsequent
nested items

Difference between ArrayList(Java dataStructure) and our custom DeepArrayList

ArrayList subList = new ArrayList();
subList.add(3);
subList.add(4);

ArrayList list = new ArrayList()
list.add(1)
list.dd(2)
list.add(subList)
list.add(5)

Iterating over these elements would result in:
for ArrayList: [1, 3, [3, 4], 5]
for DeepArrayList: [1, 2, 3, 4, 5]