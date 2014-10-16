#include <iostream>
#include <string>
using namespace std;

class intset
{
public:
    intset():count(0) {}
    intset(int s, const int a[]);
    intset(const intset &o); //复制构造函数，用于参与运算的某个集合为空的情况
    bool IsEmpty() const; //集合是否为空
    bool IsMemberOf(const int &m) const; //m是否为集合成员
    void add(const int &m); //m加入集合
    intset interset(const intset &o); //交集
    intset unionset(const intset &o); //并集
    void show(); //显示集合内容
    intset operator+(const intset &o); //并集
    intset operator-(const intset &o);  //差集
private:
    int element[100];
    int count;
};

intset::intset(int s,const int a[]):count(s)
{
    for(int i=0;i<s;++i)
        element[i]=a[i];
}

intset::intset(const intset &o)
{
    count=o.count;
    for(int i=0;i<count;++i)
        element[i]=o.element[i];
}

bool intset::IsEmpty() const
{
    return (count==0);
}

bool intset::IsMemberOf(const int &m) const
{
    if(IsEmpty())
        return false;
    for(int i=0;i<count;++i)
    {
        if(element[i]==m)
            return true;
    }
    return false;
}

void intset::add(const int &m)
{
    if (++count>100)
        return;
    element[count-1]=m;
}

intset intset::interset(const intset &o)
{
    intset inset;
    if(IsEmpty()||o.IsEmpty())
        return inset;
    for(int i=0;i<count;++i)
    {
        for(int j=0;j<o.count;++j)
            if(element[i]==o.element[j]&&!inset.IsMemberOf(o.element[j]))
                inset.add(element[i]);
    }
    return inset;
}

intset intset::unionset(const intset &o)
{
    intset tmpset(count,element);
    if (!IsEmpty()&&o.IsEmpty())
        return tmpset;
    if (IsEmpty()&&!o.IsEmpty())
        return intset(o);
    for (int i=0;i<count;++i)
    {
        for (int j=0;j< o.count;++j)
            if (element[i]!=o.element[j]&&!tmpset.IsMemberOf(o.element[j]))
                tmpset.add(o.element[j]);
    }
    return tmpset;
}

intset intset::operator+(const intset &o)
{
    return unionset(o);
}

intset intset::operator-(const intset &o)
{
    intset tmpset;
    if(!IsEmpty()&&o.IsEmpty()) //当前集合为空
        return intset(*this);
    if(IsEmpty()&&!o.IsEmpty())  //o集合为空
        return tmpset;
    for(int i=0;i<count;++i)
    {
        if(!o.IsMemberOf(element[i])) tmpset.add(element[i]);
    }
    return tmpset;
}

void intset::show()
{
    int i;
    cout<<"{";
    for(i=0;i<count;++i)
        cout<<element[i]<<",";
    cout<<"}"<<endl;
}

int main()
{
    int arr[9]={1,2,3,4,5,6,7,8,9};
    intset a,b(9,arr);
    a.add(6);
    a.add(7);
    a.add(8);
    a.add(9);
    cout<<"A:"<<endl;
    a.show();
    cout<<"B:"<<endl;
    b.show();
    intset c=a+b;
    intset d=b-a;
    cout<<"A+B:"<<endl;
    c.show();
    cout<<"B-A:"<<endl;
    d.show();
    intset e=a.unionset(b);
    intset f=a.interset(b);
    cout<<"A与B的并集:"<<endl;
    e.show();
    cout<<"A与B的交集:"<<endl;
    f.show();
    return 0;
}
